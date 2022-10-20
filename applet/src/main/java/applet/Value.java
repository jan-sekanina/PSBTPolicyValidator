package applet;

import static applet.MainApplet.PSBTdata;

public class Value {
    public short start = -1;
    public Short value_len = null;
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
        return PSBTdata[start + value_len_bytes + index];
    }

    public void setByte(short index, byte newByte) {
        PSBTdata[start + value_len_bytes + index] = newByte;
    }
}
