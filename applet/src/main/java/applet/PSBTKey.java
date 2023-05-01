package applet;

public class PSBTKey {
    public short start = -1;
    public short key_len = -1;
    public short key_len_bytes = 1;
    public short keyKype = -1; // in bips there is difference

    public void fill(short arrayIndex) {
        start = arrayIndex;
        key_len = Tools.compactWeirdoInt(start);
        if (key_len >= 0xfd) {
            key_len_bytes = 3;
        }
        keyKype = Tools.compactWeirdoInt((short) (start + key_len_bytes));
    }

    public short getSize() {
        return (short) (key_len_bytes + key_len);
    }

    public void reset() {
        start = -1;
        key_len = -1;
        key_len_bytes = 1;
        keyKype = -1;
    }

}
