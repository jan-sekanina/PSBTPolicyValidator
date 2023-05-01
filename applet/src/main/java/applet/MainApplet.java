package applet;

import javacard.framework.*;


public class MainApplet extends Applet implements MultiSelectable {
    public static final short MAX_SIZE_OF_PSBT = 1024 * 6;
    public static final short MAX_SIZE_OF_POLICY = 256;
    public static final short STORAGE_AMOUNT = 16;
    public static final short STORAGE_SIZE = 128;
    /**
     * class of all instructions and other hardcoded information
     */

    public static PSBT psbt;
    public static ArrayInDisguise[] privDataStorage; // used before policy is locked
    public static ArrayInDisguise[] pubDataStorage; // used after policy is locked
    public static byte[] PSBTdata;
    public static byte[] policy;
    public static byte[] totalOutput;
    static short transactionOffset;
    static short policyOffset;
    static short policyUploadLocked; // 0 - locked, 1 - opened

    //private byte[] data = JCSystem.makeTransientByteArray((short) (1024 * 10),
    //		JCSystem.CLEAR_ON_DESELECT);

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new MainApplet(bArray, bOffset, bLength);
    }

    public MainApplet(byte[] buffer, short offset, byte length) {
        psbt = new PSBT();
        privDataStorage = new ArrayInDisguise[STORAGE_AMOUNT];
        pubDataStorage = new ArrayInDisguise[STORAGE_AMOUNT];

        short i = 0;
        while (i < STORAGE_AMOUNT) {
            privDataStorage[i] = new ArrayInDisguise(STORAGE_SIZE);
            pubDataStorage[i] = new ArrayInDisguise(STORAGE_SIZE);
            i++;
        }

        PSBTdata = new byte[MAX_SIZE_OF_PSBT];  // to change PSBT max size change this constant
        policy = new byte[MAX_SIZE_OF_POLICY];
        totalOutput = new byte[8];
        transactionOffset = 0;
        policyOffset = 0;
        policyUploadLocked = 0;

        register();
    }

    public void process(APDU apdu) {
        byte[] apduBuffer = apdu.getBuffer();
        byte cla = apduBuffer[ISO7816.OFFSET_CLA];
        byte ins = apduBuffer[ISO7816.OFFSET_INS];
        short lc = apduBuffer[ISO7816.OFFSET_LC];
        short p1 = apduBuffer[ISO7816.OFFSET_P1];
        short p2 = apduBuffer[ISO7816.OFFSET_P2];
        short dataOffset = apduBuffer[ISO7816.OFFSET_CDATA];

        /**
         * this uploads PSBT
         */
        if (cla == AppletInstructions.CLASS_PSBT_UPLOAD) {
            if (ins == AppletInstructions.INS_REQUEST) {
                transactionOffset = 0;
            }
            if (ins == AppletInstructions.INS_UPLOAD) {
                Util.arrayCopyNonAtomic(apduBuffer, ISO7816.OFFSET_CDATA, PSBTdata, transactionOffset, (short) (lc & 0xff));
                transactionOffset += (short) (lc & 0xff);
            }

            if (ins == AppletInstructions.INS_FINISH) {
                try {
                    psbt.reset();
                    psbt.fill();
                } catch (Exception e) {
                    ISOException.throwIt((short) 0x8333);
                }
            }
        }

        if (cla == AppletInstructions.CLASS_POLICY_UPLOAD && policyUploadLocked == 1) {
            ISOException.throwIt((short) 0x8444);
        }

        /**
         * this uploads Policy represented as array of bytes
         */
        if (cla == AppletInstructions.CLASS_POLICY_UPLOAD && policyUploadLocked == 0) {
            if (ins == AppletInstructions.INS_REQUEST) {
            }
            if (ins == AppletInstructions.INS_UPLOAD) {
                Util.arrayCopyNonAtomic(apduBuffer, ISO7816.OFFSET_CDATA, policy, policyOffset, (short) (lc & 0xff));
                policyOffset += (short) (lc & 0xff);
            }
            if (ins == AppletInstructions.INS_FINISH) {
                if (!checkStorageUsage()){
                    ISOException.throwIt((short) 0x8666); // if you see this, there is some unused storage in your policy
                }
                policyUploadLocked = 1;
            }
        }

        /**
         * this uploads additional data for Policy evaluation
         */
        if (cla == AppletInstructions.CLASS_ADDITIONAL_DATA_UPLOAD && policyUploadLocked == 0) {
            if (ins == AppletInstructions.INS_REQUEST) {
                privDataStorage[p1].offset = 0;
            }
            if (ins == AppletInstructions.INS_UPLOAD) {
                Util.arrayCopyNonAtomic(apduBuffer, ISO7816.OFFSET_CDATA, privDataStorage[p1].array, privDataStorage[p1].offset, (short) (lc & 0xff));
                privDataStorage[p1].offset += (short) (lc & 0xff);
            }
            if (ins == AppletInstructions.INS_FINISH) {
            }
        }

        if (cla == AppletInstructions.CLASS_ADDITIONAL_DATA_UPLOAD && policyUploadLocked == 1) { // Maybe different Class for DATA upload after policy is locked
            if (ins == AppletInstructions.INS_REQUEST) {
                pubDataStorage[p1].offset = 0;
            }
            if (ins == AppletInstructions.INS_UPLOAD) {
                Util.arrayCopyNonAtomic(apduBuffer, ISO7816.OFFSET_CDATA, pubDataStorage[p1].array, pubDataStorage[p1].offset, (short) (lc & 0xff));
                pubDataStorage[p1].offset += (short) (lc & 0xff);
            }
            if (ins == AppletInstructions.INS_FINISH) {
            }
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_PSBT_ARRAY && ins == AppletInstructions.INS_DOWNLOAD_ARRAY) {
            short from = (short) ((apduBuffer[ISO7816.OFFSET_CDATA] & 0xff) << 8 | (apduBuffer[ISO7816.OFFSET_CDATA + 1] & 0xff));
            short to = (short) ((apduBuffer[ISO7816.OFFSET_CDATA + 2] & 0xff) << 8 | (apduBuffer[ISO7816.OFFSET_CDATA + 3] & 0xff));
            if (from < 0 || to > PSBTdata.length) {
		        return;
            }
            FromApplet.send_data(apdu, PSBTdata, from, to);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP && ins == AppletInstructions.INS_DOWNLOAD_VERSION){
            FromApplet.send_data(apdu, GlobalMap.PSBTversion);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP && ins == AppletInstructions.INS_DOWNLOAD_SIZE){
            FromApplet.send_data(apdu, PSBT.byte_size);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP && ins == AppletInstructions.INS_DOWNLOAD_NUM_INPUT_V0){
            if (GlobalMap.PSBTversion == 0) {
                FromApplet.send_data(apdu, psbt.globalMap.globalUnsignedTX.input_count);
            }
            if (GlobalMap.PSBTversion == 2) {
                FromApplet.send_data(apdu, PSBT.current_input_map);
            }
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP && ins == AppletInstructions.INS_DOWNLOAD_NUM_OUTPUT_V0) {
            if (GlobalMap.PSBTversion == 0) {
                FromApplet.send_data(apdu, psbt.globalMap.globalUnsignedTX.output_count);
            }
            if (GlobalMap.PSBTversion == 2) {
                FromApplet.send_data(apdu, PSBT.current_output_map);
            }
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_POLICY_SIZE && ins == AppletInstructions.INS_REQUEST) {
            FromApplet.send_data(apdu, policyOffset);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_POLICY) {
            short from = (short) ((apduBuffer[ISO7816.OFFSET_CDATA] & 0xff) << 8 | (apduBuffer[ISO7816.OFFSET_CDATA + 1] & 0xff));
            short to = (short) ((apduBuffer[ISO7816.OFFSET_CDATA + 2] & 0xff) << 8 | (apduBuffer[ISO7816.OFFSET_CDATA + 3] & 0xff));
            FromApplet.send_data(apdu, policy, from, to);
        }

        if (cla == AppletInstructions.CLASS_VALIDATE_POLICY) {
            FromApplet.send_data(apdu, validatePolicy());
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_INPUT){
            if (GlobalMap.PSBTversion == 0) {
                FromApplet.send_data(apdu, psbt.globalMap.globalUnsignedTX.inputs[p1]);
            }

            if (GlobalMap.PSBTversion == 2) {
                FromApplet.send_data(apdu, psbt.input_maps[p1]);
            }
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_OUTPUT){
            if (GlobalMap.PSBTversion == 0) {
                FromApplet.send_data(apdu, psbt.globalMap.globalUnsignedTX.outputs[p1]);
            }
            if (GlobalMap.PSBTversion == 2) {
                FromApplet.send_data(apdu, psbt.outputMaps[p1]);
            }
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_GLOBAL_ALL) {
            FromApplet.send_data(apdu, psbt.globalMap);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_INPUT_ALL) {
            FromApplet.send_data(apdu, psbt.input_maps[p1]);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_OUTPUT_ALL) {
            FromApplet.send_data(apdu, psbt.outputMaps[p1]);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP_KEYPAIR) {
            FromApplet.send_data(apdu, psbt.globalMap.keyPairs[p1]);
        }

        if (cla == AppletInstructions.CLASS_HAND_SHAKE) { // a bit arbitrary
            byte[] HAND_SHAKE = {'H', 'A', 'N', 'D', ' ', 'S', 'H', 'A', 'K', 'E'};
            Util.arrayCopyNonAtomic(HAND_SHAKE, (short) 0, apduBuffer, (short) 0, (short) HAND_SHAKE.length);
            apdu.setOutgoingAndSend((short) 0, (short) HAND_SHAKE.length);
        }
    }

    short validatePolicy(){
        short stepCounter = 0;
        short orSection = 0;
        if (policyOffset == 0) { //  empty policy is valid policy
            return 1;
        }

        while (stepCounter < policyOffset) {
            switch (policy[stepCounter]) {
                case Policy.minTotalOutput:
                    if (Policy.validateTotalOutput((policy[((short) (stepCounter + 1))]), (short) 1)){
                        orSection = 1;
                    }
                    stepCounter += 2;
                    break;

                case Policy.maxTotalOutput:
                    if (Policy.validateTotalOutput(policy[(short) (stepCounter + 1)], (short) -1)){
                        orSection = 1;
                    }
                    stepCounter += 2;
                    break;

                case Policy.minOutputWithSigScr:
                    if (Policy.validateValueOwSS((policy[(short) (stepCounter + 1)]), (policy[(short) (stepCounter + 2)]), (short) 1)) { //validate Value Output with Sign Script
                        orSection = 1;
                    }
                    stepCounter += 3;
                    break;

                case Policy.maxOutputWithSigScr:
                    if (Policy.validateValueOwSS((policy[(short) (stepCounter + 1)]), (policy[(short) (stepCounter + 2)]), (short) -1)) {
                        orSection = 1;
                    }
                    stepCounter += 3;
                    break;

                case Policy.minNumberofInputs:
                    if (GlobalMap.PSBTversion == 0) {
                        if (policy[(short) (stepCounter + 1)] <= psbt.globalMap.globalUnsignedTX.input_count) {
                            orSection = 1;
                        }
                    }

                    if (GlobalMap.PSBTversion == 2) {
                        if (policy[(short) (stepCounter + 1)] <= psbt.globalMap.input_maps_total) {
                            orSection = 1;
                        }
                    }
                    stepCounter += 2;
                    break;

                case Policy.maxNumberofInputs:
                    if (GlobalMap.PSBTversion == 0) {
                        if (policy[(short) (stepCounter + 1)] >= psbt.globalMap.globalUnsignedTX.input_count) {
                            orSection = 1;
                        }
                    }

                    if (GlobalMap.PSBTversion == 2) {
                        if (policy[(short) (stepCounter + 1)] >= psbt.globalMap.input_maps_total) {
                            orSection = 1;
                        }
                    }
                    stepCounter += 2;
                    break;

                case Policy.minNumberofOutputs:
                    if (GlobalMap.PSBTversion == 0) {
                        if (policy[(short) (stepCounter + 1)] <= psbt.globalMap.globalUnsignedTX.output_count) {
                            orSection = 1;
                        }
                    }

                    if (GlobalMap.PSBTversion == 2) {
                        if (policy[(short) (stepCounter + 1)] <= psbt.globalMap.outputMapsTotal) {
                            orSection = 1;
                        }
                    }
                    stepCounter += 2;
                    break;

                case Policy.maxNumberofOutputs:
                    if (GlobalMap.PSBTversion == 0) {
                        if (policy[(short) (stepCounter + 1)] >= psbt.globalMap.globalUnsignedTX.output_count) {
                            orSection = 1;
                        }
                    }

                    if (GlobalMap.PSBTversion == 2) {
                        if (policy[(short) (stepCounter + 1)] >= psbt.globalMap.outputMapsTotal) {
                            orSection = 1;
                        }
                    }
                    stepCounter += 2;
                    break;

                case Policy.signedPSBT:
                    try {
                        if (SetAndVerify.verifyECDSA(PSBTdata, transactionOffset, pubDataStorage[policy[(short) (stepCounter + 1)]].array,
                                pubDataStorage[policy[(short) (stepCounter + 1)]].offset)) {
                            orSection = 1;
                        }
                    } catch (Exception e) {
                        ISOException.throwIt((short) 0x9444);
                    }
                    stepCounter += 2;
                    break;

                case Policy.signedTimeLapse:
                    SetAndVerify.setPublicKey(privDataStorage[policy[(short) (stepCounter + 1)]].array,
                            privDataStorage[policy[(short) (stepCounter + 1)]].offset);
                    if (SetAndVerify.verifyECDSA(
                            pubDataStorage[policy[(short) (stepCounter + 2)]].array,
                            pubDataStorage[policy[(short) (stepCounter + 2)]].offset,
                            pubDataStorage[policy[(short) (stepCounter + 1)]].array,
                            pubDataStorage[policy[(short) (stepCounter + 1)]].offset)
                            &&
                            Util.arrayCompare(
                                pubDataStorage[policy[(short) (stepCounter + 2)]].array, (short) 0,
                                privDataStorage[policy[(short) (stepCounter + 2)]].array, (short) 0,
                                privDataStorage[policy[(short) (stepCounter + 2)]].offset) != -1) {
                        orSection = 1;

                    }
                    stepCounter += 3;
                    break;

                case Policy.checkSecret:
                    if ((Util.arrayCompare(privDataStorage[policy[(short) (stepCounter + 1)]].array, (short) 0,
                            pubDataStorage[policy[(short) (stepCounter + 1)]].array,(short) 0, STORAGE_SIZE)) == 0) {
                        orSection = 1;
                    }
                    stepCounter += 2;
                    break;

                case Policy.transactionVersion:
                    if (policy[(short) (stepCounter + 1)] == GlobalMap.PSBTversion) {
                        orSection = 1;
                    }
                    stepCounter += 2;
                    break;

                case Policy.policyAnd:
                    if (orSection == 0) {
                        return validationReturnProcedure((short) 0);
                    }
                    orSection = 0;
                    stepCounter++;
                    break;

                default:
                    return validationReturnProcedure((short) 0); //  unknown instruction
            }
        }
        return validationReturnProcedure(orSection);
    }

    boolean checkStorageUsage() {
        short stepCounter = 0;
        while (stepCounter < policyOffset) {
            switch (policy[stepCounter]) {
                case Policy.minTotalOutput:
                    if (privDataStorage[policy[(short) (stepCounter + 1)]].offset == 0){
                        return false;
                    }
                    stepCounter += 2;
                    break;

                case Policy.maxTotalOutput:
                    if (privDataStorage[policy[(short) (stepCounter + 1)]].offset == 0){
                        return false;
                    }
                    stepCounter += 2;
                    break;

                case Policy.minOutputWithSigScr:
                    if (privDataStorage[policy[(short) (stepCounter + 1)]].offset == 0 || privDataStorage[policy[(short) (stepCounter + 2)]].offset == 0){
                        return false;
                    }
                    stepCounter += 3;
                    break;

                case Policy.maxOutputWithSigScr:
                    if (privDataStorage[policy[(short) (stepCounter + 1)]].offset == 0 || privDataStorage[policy[(short) (stepCounter + 2)]].offset == 0){
                        return false;
                    }
                    stepCounter += 3;
                    break;

                case Policy.minNumberofInputs:
                    stepCounter += 2;
                    break;
                case Policy.maxNumberofInputs:
                    stepCounter += 2;
                    break;
                case Policy.minNumberofOutputs:
                    stepCounter += 2;
                    break;
                case Policy.maxNumberofOutputs:
                    stepCounter += 2;
                    break;

                case Policy.signedPSBT:
                    if (privDataStorage[policy[(short) (stepCounter + 1)]].offset == 0){
                        return false;
                    }
                    stepCounter += 2;
                    break;

                case Policy.signedTimeLapse:
                    if (privDataStorage[policy[(short) (stepCounter + 1)]].offset == 0 || privDataStorage[policy[(short) (stepCounter + 2)]].offset == 0){
                        return false;
                    }
                    stepCounter += 3;
                    break;

                case Policy.checkSecret:
                    if (privDataStorage[policy[(short) (stepCounter + 1)]].offset == 0) {
                        return false;
                    }
                    stepCounter += 2;
                    break;

                case Policy.transactionVersion:
                    stepCounter += 2;
                    break;

                case Policy.policyAnd:
                    stepCounter++;
                    break;
                default:
            }
        }
        return true;
    }

    private short validationReturnProcedure(short returnCode) {
        // clears all publicly accessible storages after policy is checked
        short i = 0;
        short j = 0;
        while (i < STORAGE_AMOUNT) {
            while (j < STORAGE_SIZE) {
                pubDataStorage[i].array[j] = (byte) 0; // deletes content
                j++;
            }
            pubDataStorage[i].offset = 0; // deletes size
            j = 0;
            i++;
        }
        return returnCode;
    }

    public boolean select(boolean b) {
        return true;
    }

    public void deselect(boolean b) {
    }
}
