package tests;

import applet.AppletInstructions;
import cz.muni.fi.crocs.rcard.client.CardManager;

import javax.smartcardio.Card;
import javax.smartcardio.CardException;
import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import java.util.Arrays;

public class Download {


    public byte[] downloadMap(CardManager manager, short map, byte position) throws CardException {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        cmd = new CommandAPDU(map, 0, position, 0, null, 0, 0, 4);
        rsp = manager.transmit(cmd);
        byte[] ar = rsp.getData();
        return downloadDebugArray(manager,(short) (ar[0] << 8 | ar[1]), (short) (ar[2] << 8 | ar[3]));

    }

    public byte[] downloadDebugArray(CardManager manager, short from, short to) throws CardException {
        CommandAPDU cmd;
        ResponseAPDU rsp;
        short offset = 0;
        byte[] communicationArray = new byte[4];


        byte[] res = new byte[to - from];

        while (offset < to - AppletInstructions.PACKET_BUFFER_SIZE ) {
            communicationArray[0] = (byte) ((from + offset) >> 8);
            communicationArray[1] = (byte) (from + offset);
            communicationArray[2] = (byte) ((from + offset + AppletInstructions.PACKET_BUFFER_SIZE) >> 8);
            communicationArray[3] = (byte) (from + offset + AppletInstructions.PACKET_BUFFER_SIZE);
            cmd = new CommandAPDU(AppletInstructions.CLASS_DEBUG_DOWNLOAD, AppletInstructions.INS_DOWNLOAD_ARRAY, 0, 0, communicationArray, communicationArray.length);
            rsp = manager.transmit(cmd);
            assert rsp.getSW() == 0x9000;

            System.arraycopy(rsp.getData(), (short) 0, res, offset, AppletInstructions.PACKET_BUFFER_SIZE);
            offset += AppletInstructions.PACKET_BUFFER_SIZE;
        }

        if (offset < to) {
            communicationArray[0] = (byte) (from + offset >> 8);
            communicationArray[1] = (byte) (from + offset);
            communicationArray[2] = (byte) (to >> 8);
            communicationArray[3] = (byte) to;
            cmd = new CommandAPDU(AppletInstructions.CLASS_DEBUG_DOWNLOAD, AppletInstructions.INS_DOWNLOAD_ARRAY, 0, 0, communicationArray);
            rsp = manager.transmit(cmd);
            assert rsp.getSW() == 0x9000;
            System.arraycopy(rsp.getData(), (short) 0, res,  offset, to - (offset + from));
        }
        return res;
    }
}
