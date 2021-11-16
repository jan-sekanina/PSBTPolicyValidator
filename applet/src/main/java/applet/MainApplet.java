package applet;

import javacard.framework.*;
import javacard.security.RandomData;

import java.util.Arrays;

public class MainApplet extends Applet implements MultiSelectable {
    /**
     * class of all instructions and other hardcoded information
     */
    AppletInstructions instructions = new AppletInstructions(); //move this to RAM smhw?

    public PSBT psbt;
    public byte[] data;
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
        data = new byte[1024 * 16];
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

        if (cla == instructions.CLASS_PSBT_UPLOAD && ins == instructions.INS_REQUEST) {
            //good
        }

        if (cla == instructions.CLASS_PSBT_UPLOAD && ins == instructions.INS_UPLOAD) {
            System.out.print(Arrays.toString(data) + System.lineSeparator());
            Util.arrayCopyNonAtomic(apduBuffer, (short) 5, data, myOffset, (short) (lc & 0xff));
            System.out.print(Arrays.toString(data) + System.lineSeparator());
            System.out.print("lc = " + (lc & 0xff) + System.lineSeparator());
            System.out.print("myOffset = " + myOffset + System.lineSeparator());
            myOffset += (short) (lc & 0xff);
            System.out.print("myOffset after += = " + myOffset + System.lineSeparator());
        }

        if (cla == instructions.CLASS_PSBT_UPLOAD && ins == instructions.INS_FINISH) {
            System.out.print(Arrays.toString(data));
            psbt.fillUp(data);
        }
    }

    public boolean select(boolean b) {
        return true;
    }

    public void deselect(boolean b) {
    }
}
