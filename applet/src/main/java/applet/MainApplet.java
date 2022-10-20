package applet;

import javacard.framework.*;
import javacard.security.RandomData;


public class MainApplet extends Applet implements MultiSelectable {
    public static final short MAX_SIZE_OF_PSBT = 1024 * 6;
    /**
     * class of all instructions and other hardcoded information
     */

    public PSBT psbt;
    public static byte[] PSBTdata;
    public static byte[] controlArray;
    short offset;

    //private byte[] data = JCSystem.makeTransientByteArray((short) (1024 * 10),
    //		JCSystem.CLEAR_ON_DESELECT);

    private RandomData random; // gonna delete this later on

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new MainApplet(bArray, bOffset, bLength);
    }

    public MainApplet(byte[] buffer, short offset, byte length) {
        psbt = new PSBT();
        PSBTdata = new byte[MAX_SIZE_OF_PSBT];  // to change PSBT max size change this constant
        controlArray = new byte[AppletInstructions.PACKET_BUFFER_SIZE]; // array that is sent back to computer as confirmation
        controlArray[0] = 0;
        controlArray[1] = 1;
        controlArray[2] = 2;
        controlArray[3] = 3;
        controlArray[4] = 4;
        controlArray[5] = 5;
        controlArray[6] = 6;
        controlArray[7] = 7;
        this.offset = 0;

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
            }
            if (ins == AppletInstructions.INS_UPLOAD) {

                Util.arrayCopyNonAtomic(apduBuffer, ISO7816.OFFSET_CDATA, PSBTdata, offset, (short) (lc & 0xff));
                offset += (short) (lc & 0xff);
            }

            if (ins == AppletInstructions.INS_FINISH) {

                try {
                    psbt.fill();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        //ISOException.throwIt((short) 0x9000); how it looks

        /**
         * this uploads Policy represented as array of bytes
         */
        if (cla == AppletInstructions.CLASS_POLICY_UPLOAD) {
            if (ins == AppletInstructions.INS_REQUEST) {
                //do smth here
            }
            if (ins == AppletInstructions.INS_UPLOAD) {
                //do smth here
            }
            if (ins == AppletInstructions.INS_FINISH) {
                //do smth here
            }
        }
        /**
         * this uploads data(secrets and time signed by authority) for Policy
         */
        if (cla == AppletInstructions.CLASS_SECRETandTIME_UPLOAD) {
            if (ins == AppletInstructions.INS_REQUEST) {
                //do smth here
            }
            if (ins == AppletInstructions.INS_UPLOAD) {
                //do smth here
            }
            if (ins == AppletInstructions.INS_FINISH) {
                //do smth here
            }
        }

        if (cla == AppletInstructions.CLASS_DEBUG_DOWNLOAD && ins == AppletInstructions.INS_DOWNLOAD_ARRAY) {
            short from = (short) ((apduBuffer[ISO7816.OFFSET_CDATA] & 0xff) << 8 | (apduBuffer[ISO7816.OFFSET_CDATA + 1] & 0xff));
            short to = (short) ((apduBuffer[ISO7816.OFFSET_CDATA + 2] & 0xff) << 8 | (apduBuffer[ISO7816.OFFSET_CDATA + 3] & 0xff));
            if (from < 0 || to > PSBTdata.length) {
                try {
                    throw new Exception("Can't download data outside of array!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (to - from < 0) {
                try {
                    throw new Exception("Negate amount of bytes can't be transferred!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            FromApplet.send_data(apdu, PSBTdata, from, to);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP && ins == AppletInstructions.INS_DOWNLOAD_VERSION){
            if (psbt.global_map.PSBTversion != null) {
                FromApplet.send_data(apdu, psbt.global_map.PSBTversion);
            }
            else {
                FromApplet.send_data(apdu, (short) (-1));
            }
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP && ins == AppletInstructions.INS_DOWNLOAD_SIZE){
            FromApplet.send_data(apdu, offset);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP && ins == AppletInstructions.INS_DOWNLOAD_NUM_INPUT_V0){
            FromApplet.send_data(apdu, psbt.global_map.globalUnsignedTX.input_count);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP && ins == AppletInstructions.INS_DOWNLOAD_NUM_OUTPUT_V0){
            FromApplet.send_data(apdu, psbt.global_map.globalUnsignedTX.output_count);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_INPUT_V0){
            FromApplet.send_data(apdu, psbt.global_map.globalUnsignedTX.inputs[p1]);
        }

        if (cla == AppletInstructions.CLASS_DOWNLOAD_OUTPUT_V0){
            FromApplet.send_data(apdu, psbt.global_map.globalUnsignedTX.outputs[p1]);
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

    public boolean select(boolean b) {
        return true;
    }

    public void deselect(boolean b) {
    }
}
