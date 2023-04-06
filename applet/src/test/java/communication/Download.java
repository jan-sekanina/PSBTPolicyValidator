package communication;

import applet.AppletInstructions;
import cz.muni.fi.crocs.rcard.client.CardManager;

import javax.smartcardio.CardException;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class Download {
    public short downloadVersion(CardManager manager) throws CardException {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP, AppletInstructions.INS_DOWNLOAD_VERSION, 0, 0);
        rsp = manager.transmit(cmd);
        byte[] ar = rsp.getData();
        return (short) (ar[0] << 8 | ar[1]);
    }

    public byte[] downloadPolicy(CardManager manager) throws CardException{
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_POLICY_SIZE, AppletInstructions.INS_REQUEST, 0, 0);
        rsp = manager.transmit(cmd);
        byte[] ar = rsp.getData();
        return download(manager, (byte) AppletInstructions.CLASS_DOWNLOAD_POLICY, (short) 0,
                (short) ((ar[0] & 0xff) << 8 | (ar[1] & 0xff)));
    }

    public short downloadValidation(CardManager manager) throws CardException {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_VALIDATE_POLICY, AppletInstructions.INS_REQUEST, 0, 0);
        rsp = manager.transmit(cmd);
        byte[] ar = rsp.getData();
        return (short) ((ar[0] & 0xff) << 8 | (ar[1] & 0xff));
    }

    public short downloadSize(CardManager manager) throws CardException {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP, AppletInstructions.INS_DOWNLOAD_SIZE, 0, 0);
        rsp = manager.transmit(cmd);
        byte[] ar = rsp.getData();
        return (short) ((ar[0] & 0xff) << 8 | (ar[1] & 0xff));
    }
    public short downloadNumOfInp(CardManager manager) throws CardException {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP, AppletInstructions.INS_DOWNLOAD_NUM_INPUT_V0, 0, 0);
        rsp = manager.transmit(cmd);
        byte[] ar = rsp.getData();
        return (short) ((ar[0] & 0xff) << 8 | (ar[1] & 0xff));
    }

    public short downloadNumOfOut(CardManager manager) throws CardException {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_GLOBAL_MAP, AppletInstructions.INS_DOWNLOAD_NUM_OUTPUT_V0, 0, 0);
        rsp = manager.transmit(cmd);
        byte[] ar = rsp.getData();
        return (short) ((ar[0] & 0xff) << 8 | (ar[1] & 0xff));
    }

    public byte[] downloadInput(CardManager manager, byte input_index) throws CardException {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_INPUT, 0, input_index, 0);
        rsp = manager.transmit(cmd);
        byte[] ar = rsp.getData();
        return download(manager ,(byte) AppletInstructions.CLASS_DOWNLOAD_PSBT_ARRAY,(short) ((ar[0] & 0xff) << 8 | (ar[1] & 0xff)),
                (short) ((ar[2] & 0xff) << 8 | (ar[3] & 0xff)));
    }

    public byte[] downloadOutput(CardManager manager, byte output_index) throws CardException {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(AppletInstructions.CLASS_DOWNLOAD_OUTPUT, 0, output_index, 0);
        rsp = manager.transmit(cmd);
        byte[] ar = rsp.getData();
        return download(manager, (byte) AppletInstructions.CLASS_DOWNLOAD_PSBT_ARRAY, (short) ((ar[0] & 0xff) << 8 | (ar[1] & 0xff)),
                (short) ((ar[2] & 0xff) << 8 | (ar[3] & 0xff)));
    }


    public byte[] download(CardManager manager, byte downloadInstruction, short from, short to) throws CardException {
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
            cmd = new CommandAPDU(downloadInstruction, AppletInstructions.INS_DOWNLOAD_ARRAY, 0, 0, communicationArray, communicationArray.length);
            rsp = manager.transmit(cmd);
            assert rsp.getSW() == 0x9000;
            System.arraycopy(rsp.getData(), (short) 0, res, offset, AppletInstructions.PACKET_BUFFER_SIZE);
            offset += AppletInstructions.PACKET_BUFFER_SIZE;
        }

        if (offset < to - from) {
            communicationArray[0] = (byte) ((from + offset) >> 8);
            communicationArray[1] = (byte) (from + offset);
            communicationArray[2] = (byte) (to >> 8);
            communicationArray[3] = (byte) to;
            cmd = new CommandAPDU(downloadInstruction, AppletInstructions.INS_DOWNLOAD_ARRAY, 0, 0, communicationArray, communicationArray.length);
            rsp = manager.transmit(cmd);
            assert rsp.getSW() == 0x9000;
            System.arraycopy(rsp.getData(), (short) 0, res,  offset, to - (offset + from));
        }
        return res;
    }
}
