package applet;

/**
 * Class of applet instructions to make testing and changing instructions easier
 * both MainApplet and MyTests are getting instructions from this class
 * <p>
 * It won't be possible for 'MyTests' class to get instructions from this class at the moment
 * the applet is installed on a physical card. The instructions will have to be hard coded into device
 * which is going to communicate with the applet.
 * <p>
 * Still very useful for testing and overseeing I hope.
 * <p>
 * I might consider transforming it to hashtable or more sophisticated data structure later on
 */

public class AppletInstructions {
    public AppletInstructions() {
    }

    public final short PACKET_BUFFER_SIZE = 250; // amount of data received in one packet


    /**
     * all data are uploaded with this class at the moment, that will probably change in the future
     * when I plan to create different upload class for PSBT transaction and different class for
     * applet initialization
     */


    public static final short CLASS_PSBT_UPLOAD = 0;
    public static final short CLASS_POLICY_UPLOAD = 1;
    public static final short CLASS_SECRETandTIME_UPLOAD = 2; // p1 will determine reference, where to store it

    public final short INS_REQUEST = 0;
    public final short INS_UPLOAD = 1;
    public final short INS_FINISH = 2;

    //global keytype bytes below
    public final byte PSBT_GLOBAL_UNSIGNED_TX = 0x00;
    public final byte PSBT_GLOBAL_XPUB = 0x01;
    public final byte PSBT_GLOBAL_TX_VERSION = 0x02;
    public final byte PSBT_GLOBAL_FALLBACK_LOCKTIME = 0x03;
    public final byte PSBT_GLOBAL_INPUT_COUNT = 0x04;
    public final byte PSBT_GLOBAL_OUTPUT_COUNT = 0x05;
    public final byte PSBT_GLOBAL_TX_MODIFIABLE = 0x06;
    public final byte PSBT_GLOBAL_SIGHASH_SINGLE_INPUTS = 0x07;
    public final byte PSBT_GLOBAL_VERSION = (byte) 0xFB;
    public final byte PSBT_GLOBAL_PROPRIETARY = (byte) 0xFC;
}