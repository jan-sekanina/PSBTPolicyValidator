package applet;

import static applet.MainApplet.PSBTdata;

public class Key {
    public Short start = null;
    public Short keyLen = null;
    public short keyLenBytes = 1;
    public Short keyType = null;

    public void fill(short arrayIndex) {
        start = arrayIndex;
        keyLen = Tools.compactWeirdoInt(start);
        if (keyLen >= 0xfd) {
            keyLenBytes = 3;
        }
        keyType = Tools.compactWeirdoInt((short) (start + keyLenBytes));
    }

    public short getSize() {
        assert keyLen != null;
        return (short) (keyLenBytes + keyLen);
    }

    public byte getByte(short index) {
        assert index <= keyLen;
        return PSBTdata[start + keyLenBytes + index];
    }

    public void setByte(short index, byte newByte) {
        assert index <= keyLen;
        PSBTdata[start + keyLenBytes + index] = newByte;
    }
}
