package tests;

import applet.AppletInstructions;

public class AppletComunicationTools {
    public static short getPacketBufferSize() {
        // todo make this to communicate with applet itself
        return AppletInstructions.PACKET_BUFFER_SIZE;
    }
}