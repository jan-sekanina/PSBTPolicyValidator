package applet;

public class Tools {

    public static short toShort(byte zero, byte first) {
        return (short) ((zero & 0xff) * 256 + (first & 0xff)); // TODO: have to check overflow
    }

    public static short compactWeirdoInt(short intStart, byte[] data) {
        assert (data[intStart] & 0xff) <= 0xfd;
        if ((short) (data[intStart] & 0xff) == 0xfd) {
            return (Tools.toShort(data[intStart + 1], data[intStart + 2]));
        }
        return (short) (data[intStart] & 0xff);
    }

    public static short byteSizeOfCWI(short CWI) {
        if (CWI < 0xfd) {
            return 1;
        }
        return 3;
    }
}
