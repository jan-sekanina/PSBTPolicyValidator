package applet;

public class GeneralCode {
    public static short toShort(byte zero, byte first){
        return (short) ((zero & 0xff) * 256 + (first & 0xff));
    }
}
