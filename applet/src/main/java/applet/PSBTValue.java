package applet;

import static applet.MainApplet.PSBTdata;

public class PSBTValue {
    public short start = -1;
    public short value_len = -1;
    public short valueLenBytes = 1;

    public void fill(short arrayIndex) {
        start = arrayIndex;
        value_len = Tools.compactWeirdoInt(start);
        if (value_len >= 253) {
            valueLenBytes = 3;
        }
    }

    public short getSize() {
        return (short) (valueLenBytes + value_len);
    }

    public byte getByte(short index) {
        return PSBTdata[(short) (start + valueLenBytes + index)];
    }

    public void reset() {
        start = -1;
        value_len = -1;
        valueLenBytes = 1;
    }
}
