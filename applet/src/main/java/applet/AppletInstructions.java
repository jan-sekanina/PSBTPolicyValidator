package applet;

/**
 * Class of applet instructions to make testing and changing instructions easier
 * both MainApplet and MyTests are getting instructions from this class
 *
 * It won't be possible for 'MyTests' class to get instructions from this class at the moment
 * the applet is installed on a physical card. The instructions will have to be hard coded into device
 * which is going to communicate with the applet.
 *
 * Still very useful for testing and overseeing I hope.
 *
 * I might consider transforming it to hashtable or more sophisticated data structure later on
 *
 */
public class AppletInstructions {
    public AppletInstructions(){};
    private final short PACKET_BUFFER_SIZE = 250; // amount of data received in one packet
    private final short BUFFER_SIZE = 2048 * 2; //  32 originally

    final private short CLASS_UPLOAD = 0;
    final private short UPLOAD = 0;
    final private short UPLOAD_REQUEST = 1;
    final private short UPLOAD_FINISH = 2;
    final private short CLASS_PRINT = 10;
    final private short PRINT = 10;

    public short getBUFFER_SIZE() {
        return BUFFER_SIZE;
    }

    public short getPACKET_BUFFER_SIZE() {
        return PACKET_BUFFER_SIZE;
    }

    public short getUPLOAD_FINISH() {
        return UPLOAD_FINISH;
    }

    public short getCLASS_PRINT() {
        return CLASS_PRINT;
    }

    public short getCLASS_UPLOAD() {
        return CLASS_UPLOAD;
    }

    public short getPRINT() {
        return PRINT;
    }

    public short getUPLOAD() {
        return UPLOAD;
    }

    public short getUPLOAD_REQUEST() {
        return UPLOAD_REQUEST;
    }
}
