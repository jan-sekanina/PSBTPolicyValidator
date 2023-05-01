package applet;

public class PSBTKeyPair {
    public short start = -1;
    public PSBTKey key = new PSBTKey();
    public PSBTValue value = new PSBTValue();

    public void fill(short arrayIndex) {
        start = arrayIndex;
        key.fill(arrayIndex);
        value.fill((short) (arrayIndex + key.getSize()));
    }

    public short getSize() {
        return (short) (key.getSize() + value.getSize());
    }

    public void reset() {
        key.reset();
        value.reset();
        start = -1;
    }
}
