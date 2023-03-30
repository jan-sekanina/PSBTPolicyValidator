package applet;

public class KeyPair {
    public short start = -1;
    public Key key = new Key();
    public Value value = new Value();

    public void fill(short arrayIndex) {
        start = arrayIndex;
        key.fill(arrayIndex);
        value.fill((short) (arrayIndex + key.getSize()));
    }

    public short getSize() {
        return (short) (key.getSize() + value.getSize());
        // TODO: find an easy!! way to check overflow

    }

    public void reset() {
        key.reset();
        value.reset();
        start = -1;
    }
}
