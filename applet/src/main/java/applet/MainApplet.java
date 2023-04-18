package applet;

import javacard.framework.*;
import javacard.security.RandomData;


public class MainApplet extends Applet implements MultiSelectable {
    public static final short MAX_SIZE_OF_PSBT = 1024 * 6;
    public static final short MAX_SIZE_OF_POLICY = 256;
    public static final short STORAGE_AMOUNT = 16;
    public static final short STORAGE_SIZE = 32;
    /**
     * class of all instructions and other hardcoded information
     */

    public static PSBT psbt;
    public static byte[][] additionalDataStorage;
    public static short[] dataStorageOffsets;
    public static byte[][] checkAgainstDataStorage;
    public static short[] checkAgainstDataStorageOffsets;
    public static byte[] PSBTdata;
    public static byte[] controlArray;
    public static byte[] policy;
    public static byte[] totalOutput;
    static short transactionOffset;
    static short policyOffset;
    static short policyUploadLocked; // 0 locked, 1 - opened

    //private byte[] data = JCSystem.makeTransientByteArray((short) (1024 * 10),
    //		JCSystem.CLEAR_ON_DESELECT);

    private RandomData random; // gonna delete this later on

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new MainApplet(bArray, bOffset, bLength);
    }

    public MainApplet(byte[] buffer, short offset, byte length) {
        psbt = new PSBT();
        additionalDataStorage = new byte[STORAGE_AMOUNT][STORAGE_SIZE];
        dataStorageOffsets = new short[STORAGE_AMOUNT];
        checkAgainstDataStorage = new byte[STORAGE_AMOUNT][STORAGE_SIZE];
        checkAgainstDataStorageOffsets = new short[STORAGE_AMOUNT];
        PSBTdata = new byte[MAX_SIZE_OF_PSBT];  // to change PSBT max size change this constant
        policy = new byte[MAX_SIZE_OF_POLICY];
        controlArray = new byte[AppletInstructions.PACKET_BUFFER_SIZE]; // historical array that is sent back to computer as confirmation
        totalOutput = new byte[8];
        controlArray[0] = 0;
        controlArray[1] = 1;
        controlArray[2] = 2;
        controlArray[3] = 3;
        controlArray[4] = 4;
        controlArray[5] = 5;
        controlArray[6] = 6;
        controlArray[7] = 7;
        transactionOffset = 0;
        policyOffset = 0;
        policyUploadLocked = 0;

        random = RandomData.getInstance(RandomData.ALG_SECURE_RANDOM);

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
                // TODO: return array size
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
                    //DO NOTHING
                }
            }
        }
        //ISOException.throwIt((short) 0x9000); how it looks

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
                policyUploadLocked = 1;
                checkStorageUsage();
            }
        }
        /**
         * this uploads additional data for Policy evaluation
         */
        if (cla == AppletInstructions.CLASS_ADDITIONAL_DATA_UPLOAD && policyUploadLocked == 0) {
            if (ins == AppletInstructions.INS_REQUEST) {
                dataStorageOffsets[p1] = 0;
            }
            if (ins == AppletInstructions.INS_UPLOAD) {
                Util.arrayCopyNonAtomic(apduBuffer, ISO7816.OFFSET_CDATA, additionalDataStorage[p1], dataStorageOffsets[p1], (short) (lc & 0xff));
                dataStorageOffsets[p1] += (short) (lc & 0xff);
            }
            if (ins == AppletInstructions.INS_FINISH) {
            }
        }

        if (cla == AppletInstructions.CLASS_ADDITIONAL_DATA_UPLOAD && policyUploadLocked == 1) { // Different Class for DATA upload after policy is locked
            if (ins == AppletInstructions.INS_REQUEST) {
                checkAgainstDataStorageOffsets[p1] = 0;
            }
            if (ins == AppletInstructions.INS_UPLOAD) {
                Util.arrayCopyNonAtomic(apduBuffer, ISO7816.OFFSET_CDATA, checkAgainstDataStorage[p1], checkAgainstDataStorageOffsets[p1], (short) (lc & 0xff));
                checkAgainstDataStorageOffsets[p1] += (short) (lc & 0xff);
            }
            if (ins == AppletInstructions.INS_FINISH) {
            }
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_PSBT_ARRAY && ins == AppletInstructions.INS_DOWNLOAD_ARRAY) {
            short from = (short) ((apduBuffer[ISO7816.OFFSET_CDATA] & 0xff) << 8 | (apduBuffer[ISO7816.OFFSET_CDATA + 1] & 0xff));
            short to = (short) ((apduBuffer[ISO7816.OFFSET_CDATA + 2] & 0xff) << 8 | (apduBuffer[ISO7816.OFFSET_CDATA + 3] & 0xff));
            if (from < 0 || to > PSBTdata.length) {
		return;
		/**
                try {
                    throw new Exception((short) (0x8888));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (to - from < 0) {
                try {
                    throw new Exception((short) (0x8887));
                } catch (Exception e) {
                    e.printStackTrace();
                }
		*/
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
                FromApplet.send_data(apdu, psbt.global_map.globalUnsignedTX.input_count);
            }
            if (GlobalMap.PSBTversion == 2) {
                FromApplet.send_data(apdu, PSBT.current_input_map);
            }
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP && ins == AppletInstructions.INS_DOWNLOAD_NUM_OUTPUT_V0) {
            if (GlobalMap.PSBTversion == 0) {
                FromApplet.send_data(apdu, psbt.global_map.globalUnsignedTX.output_count);
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
                FromApplet.send_data(apdu, psbt.global_map.globalUnsignedTX.inputs[p1]);
            }

            if (GlobalMap.PSBTversion == 2) {
                FromApplet.send_data(apdu, psbt.input_maps[p1]);
            }
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_OUTPUT){
            if (GlobalMap.PSBTversion == 0) {
                FromApplet.send_data(apdu, psbt.global_map.globalUnsignedTX.outputs[p1]);
            }
            if (GlobalMap.PSBTversion == 2) {
                FromApplet.send_data(apdu, psbt.output_maps[p1]);
            }
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_GLOBAL_ALL) {
            FromApplet.send_data(apdu, psbt.global_map);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_INPUT_ALL) { // TODO: change asserts to exception warning
            FromApplet.send_data(apdu, psbt.input_maps[p1]);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_OUTPUT_ALL) { // TODO: same
            FromApplet.send_data(apdu, psbt.output_maps[p1]);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP_KEYPAIR) { // TODO: same
            FromApplet.send_data(apdu, psbt.global_map.key_pairs[p1]);
        }

        if (cla == AppletInstructions.CLASS_HAND_SHAKE) {
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
            System.out.print("policy[stepcounter]: " + policy[stepCounter] + System.lineSeparator());
            System.out.print("orSection: " + orSection + System.lineSeparator());
            switch (policy[stepCounter]) {
                case Policy.minTotalOutput:
                    if (Policy.validateTotalOutput(policy[stepCounter + 1], (short) 1)){
                        orSection = 1;
                    }
                    stepCounter += 2;
                    break;

                case Policy.maxTotalOutput:
                    if (Policy.validateTotalOutput(policy[stepCounter + 1], (short) -1)){
                        orSection = 1;
                    }
                    stepCounter += 2;
                    break;

                case Policy.minOutputWithSigScr:
                    if (Policy.validateValueOwSS((policy[stepCounter + 1]), (policy[stepCounter + 2]), (short) 1)) { //validate Value Output with Sign Script
                        orSection = 1;
                    }
                    stepCounter += 3;
                    break;

                case Policy.maxOutputWithSigScr:
                    if (Policy.validateValueOwSS((policy[stepCounter + 1]), (policy[stepCounter + 2]), (short) -1)) {
                        orSection = 1;
                    }
                    stepCounter += 3;
                    break;

                case Policy.minNumberofInputs:
                    if (GlobalMap.PSBTversion == 0) {
                        if (policy[stepCounter + 1] <= psbt.global_map.globalUnsignedTX.input_count) {
                            orSection = 1;
                        }
                    }

                    if (GlobalMap.PSBTversion == 2) {
                        if (policy[stepCounter + 1] <= psbt.global_map.input_maps_total) {
                            orSection = 1;
                        }
                    }
                    stepCounter += 2;
                    break;

                case Policy.maxNumberofInputs:
                    if (GlobalMap.PSBTversion == 0) {
                        if (policy[stepCounter + 1] >= psbt.global_map.globalUnsignedTX.input_count) {
                            orSection = 1;
                        }
                    }

                    if (GlobalMap.PSBTversion == 2) {
                        if (policy[stepCounter + 1] >= psbt.global_map.input_maps_total) {
                            orSection = 1;
                        }
                    }
                    stepCounter += 2;
                    break;

                case Policy.minNumberofOutputs:
                    if (GlobalMap.PSBTversion == 0) {
                        if (policy[stepCounter + 1] <= psbt.global_map.globalUnsignedTX.output_count) {
                            orSection = 1;
                        }
                    }

                    if (GlobalMap.PSBTversion == 2) {
                        if (policy[stepCounter + 1] <= psbt.global_map.output_maps_total) {
                            orSection = 1;
                        }
                    }
                    stepCounter += 2;
                    break;

                case Policy.maxNumberofOutputs:
                    if (GlobalMap.PSBTversion == 0) {
                        if (policy[stepCounter + 1] >= psbt.global_map.globalUnsignedTX.output_count) {
                            orSection = 1;
                        }
                    }

                    if (GlobalMap.PSBTversion == 2) {
                        if (policy[stepCounter + 1] >= psbt.global_map.output_maps_total) {
                            orSection = 1;
                        }
                    }
                    stepCounter += 2;
                    break;

                case Policy.naiveTimeLapsed:  // TODO delete
                case Policy.signedTmeLapse:
                case Policy.checkSecret:
                    if ((Util.arrayCompare(additionalDataStorage[policy[stepCounter + 1]],(short) 0,checkAgainstDataStorage[policy[stepCounter + 1]],(short) 0, STORAGE_SIZE)) == 0) {
                        //does this compare little or big endian
                        orSection = 1;
                    }
                    stepCounter += 2;
                    break;

                case Policy.transactionVersion:
                    if (policy[stepCounter + 1] == GlobalMap.PSBTversion) {
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
        System.out.print("policy[stepcounter]: " + policy[stepCounter] + System.lineSeparator());
        System.out.print("orSection: " + orSection + System.lineSeparator());
        return validationReturnProcedure(orSection);
    }

    boolean checkStorageUsage() {
        short stepCounter = 0;
        while (stepCounter < policyOffset) {
            System.out.print("policy[stepcounter]: " + policy[stepCounter] + System.lineSeparator());
            switch (policy[stepCounter]) {
                case Policy.minTotalOutput:
                    if (dataStorageOffsets[policy[stepCounter + 1]] == 0){
                        System.out.println("seems like unused storage in memory with pointer: " + policy[stepCounter + 1]);
                        System.out.println("with PolicyInstruction: " + policy[stepCounter]);
                        return false;
                    }
                    stepCounter += 2;
                    break;

                case Policy.maxTotalOutput:
                    if (dataStorageOffsets[policy[stepCounter + 1]] == 0){
                        System.out.println("seems like unused storage in memory with pointer: " + policy[stepCounter + 1]);
                        System.out.println("with PolicyInstruction: " + policy[stepCounter]);
                        return false;
                    }
                    stepCounter += 2;
                    break;

                case Policy.minOutputWithSigScr:
                    if (dataStorageOffsets[policy[stepCounter + 1]] == 0 || dataStorageOffsets[policy[stepCounter + 2]] == 0){
                        System.out.println("seems like unused storage in memory with pointer: " + policy[stepCounter + 1]);
                        System.out.println("with PolicyInstruction: " + policy[stepCounter]);
                        return false;
                    }
                    stepCounter += 3;
                    break;

                case Policy.maxOutputWithSigScr:
                    if (dataStorageOffsets[policy[stepCounter + 1]] == 0 || dataStorageOffsets[policy[stepCounter + 2]] == 0){
                        System.out.println("seems like unused storage in memory with pointer: " + policy[stepCounter + 1]);
                        System.out.println("with PolicyInstruction: " + policy[stepCounter]);
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

                case Policy.naiveTimeLapsed:
                    if (dataStorageOffsets[policy[stepCounter + 1]] == 0){
                        System.out.println("seems like unused storage in memory with pointer: " + policy[stepCounter + 1]);
                        System.out.println("with PolicyInstruction: " + policy[stepCounter]);
                        return false;
                    }
                    stepCounter += 2;
                    break;

                case Policy.signedTmeLapse:
                    if (dataStorageOffsets[policy[stepCounter + 1]] == 0 || dataStorageOffsets[policy[stepCounter + 2]] == 0) {
                        System.out.println("seems like unused storage in memory with pointer: " + policy[stepCounter + 1]);
                        System.out.println("with PolicyInstruction: " + policy[stepCounter]);
                        return false;
                    }
                    stepCounter += 3;
                    break;

                case Policy.checkSecret:
                    if (dataStorageOffsets[policy[stepCounter + 1]] == 0) {
                        System.out.println("seems like unused storage in memory with pointer: " + policy[stepCounter + 1]);
                        System.out.println("with PolicyInstruction: " + policy[stepCounter]);
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
        System.out.print("policy[stepcounter]: " + policy[stepCounter] + System.lineSeparator());
        return true;

    }

    private short validationReturnProcedure(short returnCode) {
        // clears all temporary storages accessible after policy is locked
        short i = 0;
        short j = 0;
        while (i < STORAGE_AMOUNT) {
            while (j < STORAGE_SIZE) {
                checkAgainstDataStorage[i][j] = (byte) 0; // deletes content
                j++;
            }
            checkAgainstDataStorageOffsets[i] = 0; // deletes size
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
