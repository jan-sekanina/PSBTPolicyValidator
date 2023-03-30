package main;

import applet.AppletInstructions;
import com.licel.jcardsim.smartcardio.CardSimulator;
import javax.smartcardio.CardException;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class Download {
    public short downloadVersion(CardSimulator simulator) {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP, AppletInstructions.INS_DOWNLOAD_VERSION, 0, 0);
        rsp = simulator.transmitCommand(cmd);
        byte[] ar = rsp.getData();
        return (short) (ar[0] << 8 | ar[1]);
    }

    public short downloadSize(CardSimulator simulator) {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP, AppletInstructions.INS_DOWNLOAD_SIZE, 0, 0);
        rsp = simulator.transmitCommand(cmd);
        byte[] ar = rsp.getData();
        return (short) ((ar[0] & 0xff) << 8 | (ar[1] & 0xff));
    }
    public short downloadNumOfInp(CardSimulator simulator) {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP, AppletInstructions.INS_DOWNLOAD_NUM_INPUT_V0, 0, 0);
        rsp = simulator.transmitCommand(cmd);
        byte[] ar = rsp.getData();
        return (short) ((ar[0] & 0xff) << 8 | (ar[1] & 0xff));
    }

    public short downloadNumOfOut(CardSimulator simulator) {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP, AppletInstructions.INS_DOWNLOAD_NUM_OUTPUT_V0, 0, 0);
        rsp = simulator.transmitCommand(cmd);
        byte[] ar = rsp.getData();
        return (short) ((ar[0] & 0xff) << 8 | (ar[1] & 0xff));
    }

    public byte[] downloadInput(CardSimulator simulator, byte input_index) throws CardException {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_INPUT, 0, input_index, 0);
        rsp = simulator.transmitCommand(cmd);
        byte[] ar = rsp.getData();
        return download(simulator ,(short) ((ar[0] & 0xff) << 8 | (ar[1] & 0xff)),
                (short) ((ar[2] & 0xff) << 8 | (ar[3] & 0xff)));
    }

    public byte[] downloadOutput(CardSimulator simulator, byte output_index) throws CardException {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_OUTPUT, 0, output_index, 0);
        rsp = simulator.transmitCommand(cmd);
        byte[] ar = rsp.getData();
        return download(simulator,(short) ((ar[0] & 0xff) << 8 | (ar[1] & 0xff)),
                (short) ((ar[2] & 0xff) << 8 | (ar[3] & 0xff)));
    }

    public byte[] downloadMap(CardSimulator simulator, short map, byte position) {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(map, 0, position, 0, null, 0, 0, 4);
        rsp = simulator.transmitCommand(cmd);
        byte[] ar = rsp.getData();
        return download(simulator,(short) ((ar[0] & 0xff) << 8 | (ar[1] & 0xff)),
                (short) ((ar[2] & 0xff) << 8 | (ar[3] & 0xff)));
    }

    public byte[] download(CardSimulator simulator, short from, short to) {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        short offset = 0;
        byte[] communicationArray = new byte[4];

        byte[] res = new byte[to - from];

        while (offset + AppletInstructions.PACKET_BUFFER_SIZE < to) {
            communicationArray[0] = (byte) ((from + offset) >> 8);
            communicationArray[1] = (byte) (from + offset);
            communicationArray[2] = (byte) ((from + offset + AppletInstructions.PACKET_BUFFER_SIZE) >> 8);
            communicationArray[3] = (byte) (from + offset + AppletInstructions.PACKET_BUFFER_SIZE);
            cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_PSBT_ARRAY, AppletInstructions.INS_DOWNLOAD_ARRAY, 0, 0, communicationArray, communicationArray.length);
            rsp = simulator.transmitCommand(cmd);
            assert rsp.getSW() == 0x9000;
            System.arraycopy(rsp.getData(), (short) 0, res, offset, AppletInstructions.PACKET_BUFFER_SIZE);
            offset += AppletInstructions.PACKET_BUFFER_SIZE;
        }

        if (offset < to - from) {
            communicationArray[0] = (byte) ((from + offset) >> 8);
            communicationArray[1] = (byte) (from + offset);
            communicationArray[2] = (byte) (to >> 8);
            communicationArray[3] = (byte) to;
            cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_PSBT_ARRAY, AppletInstructions.INS_DOWNLOAD_ARRAY, 0, 0, communicationArray, communicationArray.length);
            rsp = simulator.transmitCommand(cmd);
            assert rsp.getSW() == 0x9000;
            System.arraycopy(rsp.getData(), (short) 0, res,  offset, to - (offset + from));
        }
        return res;
    }

    public byte[] downloadGlobalKeypair(CardSimulator simulator, int position) throws CardException {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP_KEYPAIR,  0,position, 0, null, 0, 0, 4);
        rsp = simulator.transmitCommand(cmd);
        byte[] ar = rsp.getData();
        return download(simulator,(short) (ar[0] << 8 | ar[1]), (short) (ar[2] << 8 | ar[3]));
    }
}
