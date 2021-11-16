package applet;

public class Key {
    public short start = 0;
    public Short keyLen = null;
    public short keyLenBytes = 1;
    public Short keyType = null;
    public byte[] data;

    public void fillUp(short arrayIndex, byte[] data) {
        start = arrayIndex;
        this.data = data;
        assert (short) (data[start] & 0xff) <= 0xfd;
        keyLen = Tools.compactWeirdoInt(start, data);
        if (keyLen >= 253) {
            keyLenBytes = 3;
        }
        assert (data[start + keyLenBytes] & 0xff) < 0xfd;
        keyType = Tools.compactWeirdoInt((short) (start + keyLenBytes), data);
    }

    public short getSize() {
        assert keyLen != null;
        return (short) (keyLenBytes + keyLen);
    }

    public byte getByte(short index) {
        assert index <= keyLen;
        return data[start + keyLenBytes + index];
    }

    public void setByte(short index, byte newByte) {
        assert index <= keyLen;
        data[start + keyLenBytes + index] = newByte;
    }
}
