package applet;

import javacard.framework.APDU;
import javacard.framework.Util;


public class FromApplet {
    /**
     * should be used for small amount of data of size of one packet max
     *
     * @param apdu  apdu used for communication
     * @param array array of data to send
     */
    static void sendData(APDU apdu, byte[] array, short from, short to) {
        Util.arrayCopyNonAtomic(array, from, apdu.getBuffer(), (byte) 0, (short) (to - from));
        apdu.setOutgoingAndSend((short) 0, (short) (to - from));
    }

    static void sendData(APDU apdu, short data) {
        apdu.getBuffer()[0] = (byte) (data << 8);
        apdu.getBuffer()[1] = (byte) (data);
        apdu.setOutgoingAndSend((short) 0, (short) 4);
    }

    static void sendData(APDU apdu, GeneralMap map) {
        apdu.getBuffer()[0] = (byte) (map.mapStart << 8);
        apdu.getBuffer()[1] = (byte) (map.mapStart);
        apdu.getBuffer()[2] = (byte) ((map.mapStart + map.mapSize) << 8);
        apdu.getBuffer()[3] = (byte) (map.mapStart + map.mapSize);
        apdu.setOutgoingAndSend((short) 0, (short) 4);
    }

    static void sendData(APDU apdu, PSBTKeyPair keyPair) {
        apdu.getBuffer()[0] = (byte) (keyPair.key.start << 8);
        apdu.getBuffer()[1] = (byte) (keyPair.key.start);
        apdu.getBuffer()[2] = (byte) ((keyPair.key.start + keyPair.getSize()) << 8);
        apdu.getBuffer()[3] = (byte) (keyPair.key.start + keyPair.getSize());
        apdu.setOutgoingAndSend((short) 0, (short) 4);
    }

    public static void sendData(APDU apdu, GlobalUnsignedTXInput input) {
        apdu.getBuffer()[0] = (byte) (input.previousOutputStart << 8);
        apdu.getBuffer()[1] = (byte) (input.previousOutputStart);
        apdu.getBuffer()[2] = (byte) ((input.size + input.previousOutputStart) << 8);
        apdu.getBuffer()[3] = (byte) (input.size + input.previousOutputStart);
        apdu.setOutgoingAndSend((short) 0, (short) 4);
    }

    public static void sendData(APDU apdu, GlobalUnsignedTXOutput output) {
        apdu.getBuffer()[0] = (byte) (output.valueStart << 8);
        apdu.getBuffer()[1] = (byte) (output.valueStart);
        apdu.getBuffer()[2] = (byte) ((output.size + output.valueStart) << 8);
        apdu.getBuffer()[3] = (byte) (output.size + output.valueStart);
        apdu.setOutgoingAndSend((short) 0, (short) 4);
    }
}
