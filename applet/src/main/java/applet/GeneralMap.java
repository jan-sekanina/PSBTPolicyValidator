package applet;

public class GeneralMap {
    public short NUM_OF_KEYPAIRS = 16;
    public short currentKeyPair = 0;
    public KeyPair[] keyPairs = new KeyPair[NUM_OF_KEYPAIRS];
    public short mapSize = 0;

    public GeneralMap() {
        short i = 0;
        while (i < NUM_OF_KEYPAIRS) {
            keyPairs[i] = new KeyPair();
            i++;
        }
    }

    public void fillUp(short arrayIndex, byte[] data) {
        while ((data[arrayIndex + mapSize] & 0xff) != 0x00 && currentKeyPair < NUM_OF_KEYPAIRS) {
            System.out.print("mapsize = " + mapSize + System.lineSeparator());
            System.out.print("arrayIndex = " + arrayIndex + System.lineSeparator());
            keyPairs[currentKeyPair].fillUp((short) (arrayIndex + mapSize), data);
            mapSize += keyPairs[currentKeyPair].getSize();
            System.out.print("currentKeyPairSize = " + keyPairs[currentKeyPair].getSize() + System.lineSeparator());
            currentKeyPair++;
            System.out.print("mapsize = " + mapSize + System.lineSeparator());
            System.out.print("arrayIndex = " + arrayIndex + System.lineSeparator());
        }
    }
}
