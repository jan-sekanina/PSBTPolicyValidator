package applet;

public class KeyPair {
    public Key key = new Key();
    public Value value = new Value();

    public void fill(short arrayIndex) {
        key.fill(arrayIndex);
        value.fill((short) (arrayIndex + key.getSize()));
    }

    public short getSize() {
        return (short) (key.getSize() + value.getSize());
        // TODO: find an easy!! way to check overflow

    }
}
