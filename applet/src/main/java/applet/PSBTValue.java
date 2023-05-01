package applet;

import static applet.MainApplet.PSBTdata;

public class PSBTValue {
    public short start = -1;
    public short value_len = -1;
    public short value_len_bytes = 1;

    public void fill(short arrayIndex) {
        start = arrayIndex;
        value_len = Tools.compactWeirdoInt(start);
        if (value_len >= 253) {
            value_len_bytes = 3;
        }
    }

    public short getSize() {
        return (short) (value_len_bytes + value_len);
    }

    public byte getByte(short index) {
        return PSBTdata[(short) (start + value_len_bytes + index)];
    }

    public void reset() {
        start = -1;
        value_len = -1;
        value_len_bytes = 1;
    }
}
