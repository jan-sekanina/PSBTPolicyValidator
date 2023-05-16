package applet;

public class PSBTKey {
    public short start = -1;
    public short keyLen = -1;
    public short keyLenBytes = 1;
    public short keyKype = -1; // in bips there is difference

    public void fill(short arrayIndex) {
        start = arrayIndex;
        keyLen = Tools.compactWeirdoInt(start);
        if (keyLen >= 0xfd) {
            keyLenBytes = 3;
        }
        keyKype = Tools.compactWeirdoInt((short) (start + keyLenBytes));
    }

    public short getSize() {
        return (short) (keyLenBytes + keyLen);
    }

    public void reset() {
        start = -1;
        keyLen = -1;
        keyLenBytes = 1;
        keyKype = -1;
    }

}
