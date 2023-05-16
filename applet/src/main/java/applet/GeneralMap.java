package applet;

import static applet.MainApplet.PSBTdata;

public class GeneralMap {

    public short mapStart = -1;
    public final static short NUM_OF_KEYPAIR = 8;
    public short currentKeyPair = -1;
    public PSBTKeyPair[] keyPairs = new PSBTKeyPair[NUM_OF_KEYPAIR];
    public short mapSize = 0;

    public GeneralMap() {
        short i = 0;
        while (i < NUM_OF_KEYPAIR) {
            keyPairs[i] = new PSBTKeyPair();
            i++;
        }
    }

    public void fill(short arrayIndex) {
        mapStart = (short) (arrayIndex + 1);
        while ((PSBTdata[(short) (arrayIndex + mapSize)] & 0xff) != (short)  0x00 && currentKeyPair < (short) (NUM_OF_KEYPAIR - 1)) {
            currentKeyPair++;
            keyPairs[currentKeyPair].fill((short) (arrayIndex + mapSize));
            mapSize += keyPairs[currentKeyPair].getSize();
        }
    }

    public void reset() {
        short i = 0;
        while (i < NUM_OF_KEYPAIR) {
            keyPairs[i].reset();
            i++;
        }
        mapStart = -1;
        currentKeyPair = -1;
        mapSize = 0;
    }
}
