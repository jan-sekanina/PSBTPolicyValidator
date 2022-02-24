package applet;

import javacard.framework.*;
import javacard.security.RandomData;

import java.util.Arrays; // this import won't be needed in applet itself

public class MainApplet extends Applet implements MultiSelectable {
    public static final short MAX_SIZE_OF_PSBT = 1024 * 6;
    /**
     * class of all instructions and other hardcoded information
     */
    AppletInstructions instructions = new AppletInstructions(); //move this to RAM smhw?

    public PSBT psbt;
    public static byte[] PSBTdata;
    short myOffset;

    //private byte[] data = JCSystem.makeTransientByteArray((short) (1024 * 10),
    //		JCSystem.CLEAR_ON_DESELECT);

    private RandomData random; // gonna delete this later on

    public static void install(byte[] bArray, short bOffset, byte bLength) {
        new MainApplet(bArray, bOffset, bLength);
    }

    public MainApplet(byte[] buffer, short offset, byte length) {
        instructions = new AppletInstructions(); //move this to RAM smhw?
        psbt = new PSBT();
        PSBTdata = new byte[MAX_SIZE_OF_PSBT]; // set the maximum size of PSBT here
        myOffset = 0;

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
            if (ins == instructions.INS_REQUEST) {
                //good
            }
            if (ins == instructions.INS_UPLOAD){
                Util.arrayCopyNonAtomic(apduBuffer, (short) 5, PSBTdata, myOffset, (short) (lc & 0xff));
                System.out.print(Arrays.toString(PSBTdata) + System.lineSeparator());
                myOffset += (short) (lc & 0xff);
            }
            if (ins == instructions.INS_FINISH){

                System.out.print(Arrays.toString(PSBTdata));
                try {
                    psbt.fill();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        /**
         * this uploads Policy represented as array of bytes
         */
        if (cla == AppletInstructions.CLASS_POLICY_UPLOAD) {
            if (ins == instructions.INS_REQUEST){
                //do smth here
            }
            if (ins == instructions.INS_UPLOAD){
                //do smth here
            }
            if (ins == instructions.INS_FINISH){
                //do smth here
            }
        }
        /**
         * this uploads data(secrets and time signed by authority) for Policy
         */
        if (cla == AppletInstructions.CLASS_SECRETandTIME_UPLOAD) {
            if (ins == instructions.INS_REQUEST){
                //do smth here
            }
            if (ins == instructions.INS_UPLOAD){
                //do smth here
            }
            if (ins == instructions.INS_FINISH){
                //do smth here
            }
        }
    }

    public boolean select(boolean b) {
        return true;
    }

    public void deselect(boolean b) {
    }
}
