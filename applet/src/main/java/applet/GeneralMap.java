package applet;

import static applet.MainApplet.PSBTdata;

public class GeneralMap {
    public short NUM_OF_KEYPAIR = 8;
    public short currentKeyPair = -1;
    public KeyPair[] keyPairs = new KeyPair[NUM_OF_KEYPAIR];
    public short mapSize = 0;

    public GeneralMap() {
        short i = 0;
        while (i < NUM_OF_KEYPAIR) {
            keyPairs[i] = new KeyPair();
            i++;
        }
    }

    public void fillUp(short arrayIndex) {
        while ((PSBTdata[arrayIndex + mapSize] & 0xff) != 0x00 && currentKeyPair < NUM_OF_KEYPAIR - 1) {
            currentKeyPair++;
            //System.out.print("mapsize = " + mapSize + System.lineSeparator());
            //System.out.print("arrayIndex = " + arrayIndex + System.lineSeparator());
            keyPairs[currentKeyPair].fill((short) (arrayIndex + mapSize));
            mapSize += keyPairs[currentKeyPair].getSize();
            //System.out.print("currentKeyPairSize = " + keyPairs[currentKeyPair].getSize() + System.lineSeparator());
            //System.out.print("mapsize = " + mapSize + System.lineSeparator());
            //System.out.print("arrayIndex = " + arrayIndex + System.lineSeparator());
        }
    }
}
