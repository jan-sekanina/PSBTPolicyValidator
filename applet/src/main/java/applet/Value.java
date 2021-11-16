package applet;

public class Value {
    public Short start = null;
    public Short valueLen = null;
    public short valueLenBytes = 1;
    public byte[] data;

    public void fillUp(short arrayIndex, byte[] data) {
        this.data = data;
        start = arrayIndex;
        valueLen = Tools.compactWeirdoInt(start, data);
        if (valueLen >= 253) {
            valueLenBytes = 3;
        }
    }

    public short getSize() {
        assert valueLen != null;
        return (short) (valueLenBytes + valueLen);
    }

    public byte getByte(short index) {
        assert index <= valueLen;
        return data[start + valueLenBytes + index];
    }

    public void setByte(short index, byte newByte) {
        assert index <= valueLen;
        data[start + valueLenBytes + index] = newByte;
    }
}
