package applet;

import static applet.MainApplet.PSBTdata;

public class GeneralMap {

    public short map_start = -1;
    public short NUM_OF_KEYPAIR = 8;
    public short currentKeyPair = -1;
    public PSBTKeyPair[] keyPairs = new PSBTKeyPair[NUM_OF_KEYPAIR];
    public short map_size = 0;

    public GeneralMap() {
        short i = 0;
        while (i < NUM_OF_KEYPAIR) {
            keyPairs[i] = new PSBTKeyPair();
            i++;
        }
    }

    public void fill(short arrayIndex) {
        map_start = (short) (arrayIndex + 1);
        while ((PSBTdata[(short) (arrayIndex + map_size)] & 0xff) != (short)  0x00 && currentKeyPair < (short) (NUM_OF_KEYPAIR - 1)) {
            currentKeyPair++;
            keyPairs[currentKeyPair].fill((short) (arrayIndex + map_size));
            map_size += keyPairs[currentKeyPair].getSize();
        }
    }

    public void reset() {
        short i = 0;
        while (i < NUM_OF_KEYPAIR) {
            keyPairs[i].reset();
            i++;
        }
        map_start = -1;
        currentKeyPair = -1;
        map_size = 0;
    }
}
