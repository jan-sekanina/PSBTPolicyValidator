package tests;
import applet.*;
import cz.muni.fi.crocs.rcard.client.CardManager;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import java.nio.charset.StandardCharsets;

import static tests.AppletComunicationTools.getPacketBufferSize;

/**
 * class of my tests
 */

public class Upload extends BaseTest {

    public Upload() {
    }

    /**
     * maybe create new class for card management from the computer?
     * @throws Exception
     */
    public void handshake() throws Exception{
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_HAND_SHAKE, AppletInstructions.INS_REQUEST, 0, 0);

        CardManager manager = connect();
        rsp = manager.transmit(cmd);
        String res = new String(rsp.getBytes(), StandardCharsets.UTF_8);
        System.out.print("response: " + res + System.lineSeparator());
        assert rsp.getSW() == 0x9000;
    }

    /**
     *
     * @return amount of data that can be sent in one transaction
     */


    /**
     *
     * @param data data that are sent to the applet
     * @param uploadClass information for applet what to do with the data
     * @return array of byte of applet response, array size is the size of one packet
     * @throws Exception
     */
    public byte[] sendData(byte[] data, byte uploadClass) throws Exception {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        short packetSize = getPacketBufferSize();
        cmd = new CommandAPDU(uploadClass, AppletInstructions.INS_REQUEST, 0, 0);

        CardManager manager = connect();
        rsp = manager.transmit(cmd);
        assert rsp.getSW() == 0x9000;

        int offset = 0;

        while (offset + packetSize < data.length) {
            cmd = new CommandAPDU(uploadClass, AppletInstructions.INS_UPLOAD, 0, 0, data, offset, packetSize, 0);
            rsp = manager.transmit(cmd);
            assert rsp.getSW() == 0x9000;
            offset += packetSize;
        }

        cmd = new CommandAPDU(uploadClass, AppletInstructions.INS_UPLOAD, 0, 0, data, offset, data.length - offset);
        rsp = manager.transmit(cmd);
        assert rsp.getSW() == 0x9000;

        cmd = new CommandAPDU(uploadClass, AppletInstructions.INS_FINISH, 0, 0);
        rsp = manager.transmit(cmd);
        assert rsp.getSW() == 0x9000;

        manager.disconnect(true);

//      cmd = new CommandAPDU(1, 1, 1, 1, 1, 1, 1, 1);

        return rsp.getBytes();
    }

    public byte[] sendData(byte[] data, byte uploadClass, CardManager manager) throws Exception {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        short packetSize = getPacketBufferSize();
        cmd = new CommandAPDU(uploadClass, AppletInstructions.INS_REQUEST, 0, 0);
        rsp = manager.transmit(cmd);
        assert rsp.getSW() == 0x9000;

        int offset = 0;

        while (offset + packetSize < data.length) {
            cmd = new CommandAPDU(uploadClass, AppletInstructions.INS_UPLOAD, 0, 0, data, offset, packetSize, 0);
            rsp = manager.transmit(cmd);
            assert rsp.getSW() == 0x9000;
            offset += packetSize;
        }

        cmd = new CommandAPDU(uploadClass, AppletInstructions.INS_UPLOAD, 0, 0, data, offset, data.length - offset);
        rsp = manager.transmit(cmd);
        assert rsp.getSW() == 0x9000;

        cmd = new CommandAPDU(uploadClass, AppletInstructions.INS_FINISH, 0, 0);
        rsp = manager.transmit(cmd);
        System.out.print(rsp.getSW() + System.lineSeparator());
        System.out.print(rsp.getSW1() + System.lineSeparator());
        System.out.print(rsp.getSW2() + System.lineSeparator());
        assert rsp.getSW() == 0x9000;
//      cmd = new CommandAPDU(1, 1, 1, 1, 1, 1, 1, 1);

        return rsp.getBytes();
    }
}
