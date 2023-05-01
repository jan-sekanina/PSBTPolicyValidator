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

    static void send_data(APDU apdu, byte[] array, short from, short to) {
        Util.arrayCopyNonAtomic(array, from, apdu.getBuffer(), (byte) 0, (short) (to - from));
        apdu.setOutgoingAndSend((short) 0, (short) (to - from));
    }

    static void send_data(APDU apdu, short data) {
        apdu.getBuffer()[0] = (byte) (data << 8);
        apdu.getBuffer()[1] = (byte) (data);
        apdu.setOutgoingAndSend((short) 0, (short) 4);
    }

    static void send_data(APDU apdu, GeneralMap map) {
        apdu.getBuffer()[0] = (byte) (map.map_start << 8);
        apdu.getBuffer()[1] = (byte) (map.map_start);
        apdu.getBuffer()[2] = (byte) ((map.map_start + map.map_size) << 8);
        apdu.getBuffer()[3] = (byte) (map.map_start + map.map_size);
        apdu.setOutgoingAndSend((short) 0, (short) 4);
    }

    static void send_data(APDU apdu, PSBTKeyPair keyPair) {
        apdu.getBuffer()[0] = (byte) (keyPair.key.start << 8);
        apdu.getBuffer()[1] = (byte) (keyPair.key.start);
        apdu.getBuffer()[2] = (byte) ((keyPair.key.start + keyPair.getSize()) << 8);
        apdu.getBuffer()[3] = (byte) (keyPair.key.start + keyPair.getSize());
        apdu.setOutgoingAndSend((short) 0, (short) 4);
    }

    public static void send_data(APDU apdu, GlobalUnsignedTXInput input) {
        apdu.getBuffer()[0] = (byte) (input.previous_output_start << 8);
        apdu.getBuffer()[1] = (byte) (input.previous_output_start);
        apdu.getBuffer()[2] = (byte) ((input.size + input.previous_output_start) << 8);
        apdu.getBuffer()[3] = (byte) (input.size + input.previous_output_start);
        apdu.setOutgoingAndSend((short) 0, (short) 4);
    }

    public static void send_data(APDU apdu, GlobalUnsignedTXOutput output) {
        apdu.getBuffer()[0] = (byte) (output.valueStart << 8);
        apdu.getBuffer()[1] = (byte) (output.valueStart);
        apdu.getBuffer()[2] = (byte) ((output.size + output.valueStart) << 8);
        apdu.getBuffer()[3] = (byte) (output.size + output.valueStart);
        apdu.setOutgoingAndSend((short) 0, (short) 4);
    }
}
