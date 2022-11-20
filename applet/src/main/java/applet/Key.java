package applet;

import static applet.MainApplet.PSBTdata;

public class Key {
    public short start = -1;
    public short key_len = -1;
    public short key_len_bytes = 1;
    public short key_type = -1;

    public void fill(short arrayIndex) {
        start = arrayIndex;
        key_len = Tools.compactWeirdoInt(start);
        if (key_len >= 0xfd) {
            key_len_bytes = 3;
        }
        key_type = Tools.compactWeirdoInt((short) (start + key_len_bytes));
    }

    public short getSize() {
        return (short) (key_len_bytes + key_len);
    }

}
