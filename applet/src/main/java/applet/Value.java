package applet;

import static applet.MainApplet.PSBTdata;

public class Value {
    public Short start = null;
    public Short valueLen = null;
    public short valueLenBytes = 1;

    public void fill(short arrayIndex) {
        start = arrayIndex;
        valueLen = Tools.compactWeirdoInt(start);
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
        return PSBTdata[start + valueLenBytes + index];
    }

    public void setByte(short index, byte newByte) {
        assert index <= valueLen;
        PSBTdata[start + valueLenBytes + index] = newByte;
    }
}
