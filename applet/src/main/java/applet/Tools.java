package applet;

import static applet.MainApplet.PSBTdata;

public class Tools {

    /**
     * function to transform two bytes in little endian short
     * @param zero zeroth(lol) byte
     * @param first firsth byte
     * @return
     */
    public static short toShort(byte zero, byte first) {
        System.out.print("toShort returns: " + (short) ((zero & 0xff) * 256 + (first & 0xff))
                + System.lineSeparator());
        return (short) ((zero & 0xff) * 256 + (first & 0xff)); // TODO: have to check overflow
    }


    /**
     * compactSizeInt encoded in Little Endian of max size short
     * more documentation here:
     * https://en.bitcoin.it/wiki/Protocol_documentation#Variable_length_integer
     * @param intStart index in psbt data from where get the Int
     * @return converted short
     */
    public static short compactWeirdoInt(short intStart) {
        assert (PSBTdata[intStart] & 0xff) <= 0xfd;
        if ((short) (PSBTdata[intStart] & 0xff) == 0xfd) {
            return (Tools.toShort(PSBTdata[intStart + 1], PSBTdata[intStart + 2]));
        }
        return (short) (PSBTdata[intStart] & 0xff);
    }

    public static short byteSizeOfCWI(short CWI) {
        if (CWI < 0xfd) {
            return 1;
        }
        return 3;
    }
}
