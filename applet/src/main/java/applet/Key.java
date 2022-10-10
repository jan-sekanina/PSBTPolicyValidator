package applet;

import static applet.MainApplet.PSBTdata;

public class Key {
    public short start = -1;
    public Short key_len = null;
    public short key_len_bytes = 1;
    public Short key_type = null;

    public void fill(short arrayIndex) {
        start = arrayIndex;
        key_len = Tools.compactWeirdoInt(start);
        if (key_len >= 0xfd) {
            key_len_bytes = 3;
        }
        key_type = Tools.compactWeirdoInt((short) (start + key_len_bytes));
    }

    public short getSize() {
        assert key_len != null;
        return (short) (key_len_bytes + key_len);
    }

    public byte getByte(short index) {
        assert index <= key_len;
        return PSBTdata[start + key_len_bytes + index];
    }

    public void setByte(short index, byte newByte) {
        assert index <= key_len;
        PSBTdata[start + key_len_bytes + index] = newByte;
    }
}
