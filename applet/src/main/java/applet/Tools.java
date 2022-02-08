package applet;

public class Tools {

    public static short toShort(byte zero, byte first) {
        System.out.print("toShort returns: " + (short) ((zero & 0xff) * 256 + (first & 0xff))
                + System.lineSeparator());
        return (short) ((zero & 0xff) * 256 + (first & 0xff)); // TODO: have to check overflow
    }

    public static short compactWeirdoIntOld(short intStart, byte[] data) {
        assert (data[intStart] & 0xff) <= 0xfd;
        if ((short) (data[intStart] & 0xff) == 0xfd) {
            System.out.print("CWI returns " + (Tools.toShort(data[intStart + 1],
                    data[intStart + 2])) + System.lineSeparator());
            return (Tools.toShort(data[intStart + 1], data[intStart + 2]));
        }
        System.out.print("CWI returns " + (short) (data[intStart] & 0xff) + System.lineSeparator());
        return (short) (data[intStart] & 0xff);
    }

    public static short compactWeirdoInt(short intStart, byte[] data) {
        assert (data[intStart] & 0xff) <= 0xfd;
        if ((short) (data[intStart] & 0xff) == 0xfd) {
            System.out.print("CWI returns " + (Tools.toShort(data[intStart + 2],
                    data[intStart + 1])) + System.lineSeparator());
            return (Tools.toShort(data[intStart + 2], data[intStart + 1]));
        }
        System.out.print("CWI returns " + (short) (data[intStart] & 0xff) + System.lineSeparator());
        return (short) (data[intStart] & 0xff);
    }

    public static short byteSizeOfCWI(short CWI) {
        if (CWI < 0xfd) {
            return 1;
        }
        return 3;
    }
}
