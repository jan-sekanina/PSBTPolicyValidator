package applet;

public class ArrayInDisguise {
    public byte[] array;
    public short offset;

    ArrayInDisguise(short size) {
        array = new byte[size];
        offset = 0;
    }
}
