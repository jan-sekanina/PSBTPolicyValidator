package applet;

import javacard.framework.APDU;
import javacard.framework.Util;

import java.util.Arrays;

import static applet.MainApplet.PSBTdata;

public class FromApplet {
    /**
     * should be used for small amount of data of size of one packet max
     * @param apdu apdu used for communication
     * @param array array of data to send
     */

    static void send_data(APDU apdu, byte[] array, short from, short to) {
        Util.arrayCopyNonAtomic(array, (short) 0, apdu.getBuffer(), (short) 0, (short) (to - from));
        apdu.setOutgoingAndSend((short) 0, (short) (to - from));
    }

    static void send_data(APDU apdu, GeneralMap map) {
        apdu.getBuffer()[0] = (byte) (map.map_start << 8);
        apdu.getBuffer()[1] = (byte) (map.map_start);
        apdu.getBuffer()[2] = (byte) (map.map_size << 8);
        apdu.getBuffer()[3] = (byte) (map.map_size);
        apdu.setOutgoingAndSend((short) 0, (short) 4);
    }

    static void send_data(APDU apdu, KeyPair key_pair) {
        //TODO rewrite this to take a byte of KeyPair type instead of itself
        send_data(apdu, PSBTdata, key_pair.start, key_pair.getSize());
    }
}
