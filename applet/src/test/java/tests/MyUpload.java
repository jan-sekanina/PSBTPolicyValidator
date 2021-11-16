package tests;

import applet.AppletInstructions;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;


/**
 * class of my tests
 */
public class MyTests extends BaseTest {
    final AppletInstructions instructions = new AppletInstructions();
    final short CLASS_UPLOAD = instructions.CLASS_UPLOAD;
    final short UPLOAD = instructions.UPLOAD;
    final short UPLOAD_REQUEST = instructions.UPLOAD_REQUEST;
    final short CLASS_PRINT = instructions.CLASS_PRINT;
    final short PRINT = instructions.PRINT;

    public MyTests() {
    }


    /**
     * maybe create new class for card management from the computer?
     * @param data stream of bytes that applet stores
     * @throws Exception
     */
    public void sendData(byte[] data) throws Exception {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        int offset;
        int packet_size;
        cmd = new CommandAPDU(CLASS_UPLOAD, UPLOAD_REQUEST, 0, 0);
        rsp = connect().transmit(cmd);
        packet_size = rsp.getSW1(); // returns how big packets can be sent
        for (offset = 0; offset * packet_size <= data.length; offset++) {
            cmd = new CommandAPDU(CLASS_UPLOAD, UPLOAD, offset, data.length / packet_size, data, offset, packet_size, 0);
            rsp = connect().transmit(cmd);
            assert rsp.getSW() == 0x9000;
        }
        cmd = new CommandAPDU(CLASS_PRINT, PRINT, 0, 0);
        rsp = connect().transmit(cmd);
        assert rsp.getSW() == 0x9000;
    }

    public void downloadData() throws Exception {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        int offset;
        int packet_size;
        cmd = new CommandAPDU(CLASS_UPLOAD, UPLOAD_REQUEST, 0, 0);
        rsp = connect().transmit(cmd);
        packet_size = rsp.getSW1(); // returns how big packets will be downloaded
        while (true){
            continue;
        }
    }
}


