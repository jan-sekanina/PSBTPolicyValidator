package main;

import applet.AppletInstructions;
import com.licel.jcardsim.smartcardio.CardSimulator;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

/**
 * class of my tests
 */

public class Upload {

    public Upload() {
    }

    /**
     *
     * @param data data that are sent to the applet
     * @param uploadClass information for applet what to do with the data
     * @return array of byte of applet response, array size is the size of one packet
     * @throws Exception
     */

    public byte[] sendData(byte[] data, byte uploadClass, CardSimulator simulator) throws Exception {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        short packetSize = AppletInstructions.PACKET_BUFFER_SIZE;
        cmd = new CommandAPDU(uploadClass, AppletInstructions.INS_REQUEST, 0, 0);
        rsp = simulator.transmitCommand(cmd);
        assert rsp.getSW() == 0x9000;

        int offset = 0;

        while (offset + packetSize < data.length) {
            cmd = new CommandAPDU(uploadClass, AppletInstructions.INS_UPLOAD, 0, 0, data, offset, packetSize, 0);
            rsp = simulator.transmitCommand(cmd);
            assert rsp.getSW() == 0x9000;
            offset += packetSize;
        }

        cmd = new CommandAPDU(uploadClass, AppletInstructions.INS_UPLOAD, 0, 0, data, offset, data.length - offset);
        rsp = simulator.transmitCommand(cmd);
        assert rsp.getSW() == 0x9000;

        cmd = new CommandAPDU(uploadClass, AppletInstructions.INS_FINISH, 0, 0);
        rsp = simulator.transmitCommand(cmd);
        assert rsp.getSW() == 0x9000;
//      cmd = new CommandAPDU(1, 1, 1, 1, 1, 1, 1, 1);

        return rsp.getBytes();
    }
}