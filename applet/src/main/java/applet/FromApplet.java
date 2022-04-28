package applet;

import javacard.framework.APDU;
import javacard.framework.Util;
import static applet.MainApplet.PSBTdata;

public class FromApplet {
    /**
     * should be used for small amount of data of size of one packet max
     * @param apdu apdu used for communication
     * @param array array of data to send
     */
    static void send_data(APDU apdu, byte[] array)
    {
        Util.arrayCopyNonAtomic(array, (short) 0, apdu.getBuffer(), (short) 0, (short) array.length);
        apdu.setOutgoingAndSend((short) 0, (short) array.length);
    }

}
