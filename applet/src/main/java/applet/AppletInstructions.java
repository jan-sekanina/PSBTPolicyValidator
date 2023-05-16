package applet;

/**
 * Class of applet instructions to make testing and changing instructions easier
 * both MainApplet and MyTests are getting instructions from this class
 *
 * I might consider transforming it to hashtable or more sophisticated data structure later on // this one aged well
 */

public class AppletInstructions {

    public AppletInstructions() {
    }

    public static final short PACKET_BUFFER_SIZE = 250; // amount of data received in one packet

    public static final short CLASS_PSBT_UP = 0;

    public static final short CLASS_POLICY_UP = 1;
    // simple scenario where applet returns "HAND SHAKE" in bytes
    public static final short CLASS_HAND_SHAKE = 3;
    public static final short CLASS_ADD_DATA_UP = 2; // p1 will determine reference, where to store it
    public static final short CLASS_DOWN_PSBT_ARRAY = 4;

    public static final short CLASS_SET_MODE_SECRET = 14;
    public static final short CLASS_SET_MODE_TIME = 15;

    public static final short CLASS_DOWNLOAD_GLOBAL_MAP = 5; // p1 which global map, here always 0
    public static final short INS_DOWNLOAD_NUM_INPUT_V0 = 5;
    public static final short INS_DOWNLOAD_NUM_OUTPUT_V0 = 4;
    public static final short INS_DOWNLOAD_VERSION = 6;
    public static final short INS_DOWNLOAD_SIZE = 7;

    public static final short CLASS_DOWNLOAD_INPUT_MAP = 6; // p1 is position of map, map must be present, first is 0
    public static final short CLASS_DOWNLOAD_OUTPUT_MAP = 7; // --above--

    public static final short CLASS_DOWNLOAD_INPUT = 12;
    public static final short CLASS_DOWNLOAD_OUTPUT = 13;
    public static final short CLASS_DOWNLOAD_GLOBAL_ALL = 8;
    public static final short CLASS_DOWNLOAD_INPUT_ALL = 9;
    public static final short CLASS_DOWNLOAD_OUTPUT_ALL = 10;
    public static final short CLASS_DOWNLOAD_GLOBAL_MAP_KEYPAIR = 11;
    public static final short CLASS_DOWNLOAD_POLICY_SIZE = 14;
    public static final short CLASS_DOWNLOAD_POLICY = 15;
    public static final short CLASS_VALIDATE_POLICY = 16;


    public static final short INS_REQUEST = 0;
    public static final short INS_UPLOAD = 1;
    public static final short INS_FINISH = 2;
    public static final short INS_DOWNLOAD_ARRAY = 3;

    //  applet error throws below

    /*
    public static final short SOME_CRYPTO_ERROR = (short) 0x8444;
    public static final short ALREADY_UPLOADED_POLICY_ERROR = (short) 0x8555;
    public static final short STORAGE_UNUSED_ERROR = (short) 0x8666;
    cant do cos (short) 0x6666 != (int) 0x6666...
     */

    //  global keytype bytes below
    public static final byte PSBT_GLOBAL_UNSIGNED_TX = 0x00; // p2
    public static final byte PSBT_GLOBAL_XPUB = 0x01;
    public static final byte PSBT_GLOBAL_TX_VERSION = 0x02;
    public static final byte PSBT_GLOBAL_FALLBACK_LOCKTIME = 0x03;
    public static final byte PSBT_GLOBAL_INPUT_COUNT = 0x04;
    public static final byte PSBT_GLOBAL_OUTPUT_COUNT = 0x05;
    public static final byte PSBT_GLOBAL_TX_MODIFIABLE = 0x06;
    public static final byte PSBT_GLOBAL_SIGHASH_SINGLE_INPUTS = 0x07;
    public static final byte PSBT_GLOBAL_VERSION = (byte) 0xFB;
    public static final byte PSBT_GLOBAL_PROPRIETARY = (byte) 0xFC;


    public static final byte PSBT_OUT_AMOUNT = (byte) 0x03;
    public static final byte PSBT_OUT_SCRIPT = (byte) 0x04;

}