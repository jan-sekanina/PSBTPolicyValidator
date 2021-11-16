package applet;

public class KeyPair {
    public Key key = new Key();
    public Value value = new Value();

    public void fillUp(short arrayIndex, byte[] data) {
        key.fillUp(arrayIndex, data);
        value.fillUp((short) (arrayIndex + key.getSize()), data);
    }

    public short getSize() {
        return (short) (key.getSize() + value.getSize());
        // TODO: find a way to check overflow
    }
}
