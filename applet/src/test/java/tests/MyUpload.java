package tests;
import applet.*;
import cz.muni.fi.crocs.rcard.client.CardManager;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

/**
 * class of my tests
 */

public class MyUpload extends BaseTest {

    final AppletInstructions instructions = new AppletInstructions();

    final short CLASS_UPLOAD_PSBT = instructions.CLASS_PSBT_UPLOAD;
    final short INS_REQUEST = instructions.INS_REQUEST;
    final short INS_UPLOAD = instructions.INS_UPLOAD;
    final short INS_FINISH = instructions.INS_FINISH;


    public MyUpload() {
    }

    /**
     * maybe create new class for card management from the computer?
     * @param data stream of bytes that applet stores
     * @throws Exception
     */

    public void sendData(byte[] data) throws Exception {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        short packetSize;
        cmd = new CommandAPDU(CLASS_UPLOAD_PSBT, INS_REQUEST, 0, 0); // p1 = num of inputs, p2 = num of outputs
        CardManager manager = connect();
        rsp = manager.transmit(cmd);
        assert rsp.getSW() == 0x9000;
        packetSize = instructions.PACKET_BUFFER_SIZE;
        int offset = 0;
        while (offset + packetSize < data.length) {
            cmd = new CommandAPDU(CLASS_UPLOAD_PSBT, INS_UPLOAD, 0, 0, data, offset, packetSize, 0);
            rsp = manager.transmit(cmd);
            assert rsp.getSW() == 0x9000;
            offset += packetSize;
        }

        cmd = new CommandAPDU(CLASS_UPLOAD_PSBT, INS_UPLOAD, 0, 0, data, offset, data.length - offset);
        rsp = manager.transmit(cmd);
        assert rsp.getSW() == 0x9000;

        cmd = new CommandAPDU(CLASS_UPLOAD_PSBT, INS_FINISH, 0, 0);
        rsp = manager.transmit(cmd);
        assert rsp.getSW() == 0x9000;

        manager.disconnect(true);
//        cmd = new CommandAPDU(1, 1, 1, 1, 1, 1, 1, 1);

    }
}