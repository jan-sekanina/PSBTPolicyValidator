package applet;

import javacard.framework.*;
import javacard.security.RandomData;

import java.util.Arrays; // this import won't be needed in applet itself

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
                //good
            }
            if (ins == AppletInstructions.INS_UPLOAD){

                Util.arrayCopyNonAtomic(apduBuffer, (short) 5, PSBTdata, offset, (short) (lc & 0xff));
                System.out.print(Arrays.toString(PSBTdata) + System.lineSeparator());
                offset += (short) (lc & 0xff);
            }
            if (ins == AppletInstructions.INS_FINISH){

                System.out.print(Arrays.toString(PSBTdata));
                try {
                    psbt.fill();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //controlArray[0] = (byte) 42;
                //controlArray[1] = (byte) 69;
                controlArray[2] = (byte) 42;
                controlArray[3] = (byte) 69;

                FromApplet.send_data(apdu, controlArray);
                // sends control array back to computer
            }
        }
        //ISOException.throwIt((short) 0x9000); how it looks

        /**
         * this uploads Policy represented as array of bytes
         */
        if (cla == AppletInstructions.CLASS_POLICY_UPLOAD) {
            if (ins == AppletInstructions.INS_REQUEST){
                //do smth here
            }
            if (ins == AppletInstructions.INS_UPLOAD){
                //do smth here
            }
            if (ins == AppletInstructions.INS_FINISH){
                //do smth here
            }
        }
        /**
         * this uploads data(secrets and time signed by authority) for Policy
         */
        if (cla == AppletInstructions.CLASS_SECRETandTIME_UPLOAD) {
            if (ins == AppletInstructions.INS_REQUEST){
                //do smth here
            }
            if (ins == AppletInstructions.INS_UPLOAD){
                //do smth here
            }
            if (ins == AppletInstructions.INS_FINISH){
                //do smth here
            }
        }

        if (cla == AppletInstructions.HAND_SHAKE) {
            byte[] HAND_SHAKE = {'H', 'A', 'N', 'D', ' ', 'S', 'H', 'A', 'K', 'E'};
            Util.arrayCopyNonAtomic(HAND_SHAKE, (short) 0, apduBuffer, (short) 0, (short) HAND_SHAKE.length);
            apdu.setOutgoingAndSend((short) 0, (short) HAND_SHAKE.length);
            return;
        }
    }

    public boolean select(boolean b) {
        return true;
    }

    public void deselect(boolean b) {
    }
}
