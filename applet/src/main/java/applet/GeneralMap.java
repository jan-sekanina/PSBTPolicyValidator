package applet;

import static applet.MainApplet.PSBTdata;

public class GeneralMap {
    public short NUM_OF_KEYPAIR = 8;
    public short current_key_pair = -1;
    public KeyPair[] key_pairs = new KeyPair[NUM_OF_KEYPAIR];
    public short map_size = 0;

    public GeneralMap() {
        short i = 0;
        while (i < NUM_OF_KEYPAIR) {
            key_pairs[i] = new KeyPair();
            i++;
        }
    }

    public void fillUp(short arrayIndex) {
        while ((PSBTdata[arrayIndex + map_size] & 0xff) != 0x00 && current_key_pair < NUM_OF_KEYPAIR - 1) {
            current_key_pair++;
            //System.out.print("mapsize = " + mapSize + System.lineSeparator());
            //System.out.print("arrayIndex = " + arrayIndex + System.lineSeparator());
            key_pairs[current_key_pair].fill((short) (arrayIndex + map_size));
            map_size += key_pairs[current_key_pair].getSize();
            //System.out.print("currentKeyPairSize = " + keyPairs[currentKeyPair].getSize() + System.lineSeparator());
            //System.out.print("mapsize = " + mapSize + System.lineSeparator());
            //System.out.print("arrayIndex = " + arrayIndex + System.lineSeparator());
        }
    }
}
