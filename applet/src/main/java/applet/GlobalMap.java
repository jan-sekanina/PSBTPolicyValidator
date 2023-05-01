package applet;

import static applet.AppletInstructions.*;
import static applet.MainApplet.PSBTdata;

public class GlobalMap extends GeneralMap {
    short input_maps_total = -1;
    short outputMapsTotal = -1;
    static short PSBTversion = 0;
    GlobalUnsignedTX globalUnsignedTX = new GlobalUnsignedTX();

    public void fill(short arrayIndex) {
        map_start = (short) (arrayIndex + 1);
        while ((PSBTdata[(short) (arrayIndex + map_size)] & 0xff) != 0x00 && currentKeyPair < (short) (NUM_OF_KEYPAIR - 1)) {
            currentKeyPair++;
            keyPairs[currentKeyPair].fill((short) (arrayIndex + map_size));

            if (keyPairs[currentKeyPair].key.keyKype == PSBT_GLOBAL_UNSIGNED_TX) {
                globalUnsignedTX.fill((short) (arrayIndex + map_size + 2 +
                        keyPairs[currentKeyPair].value.value_len_bytes));
            }

            if (keyPairs[currentKeyPair].key.keyKype == PSBT_GLOBAL_INPUT_COUNT) {
                input_maps_total = keyPairs[currentKeyPair].value.getByte((short) 0);
            }

            if (keyPairs[currentKeyPair].key.keyKype == PSBT_GLOBAL_OUTPUT_COUNT) {
                outputMapsTotal = keyPairs[currentKeyPair].value.getByte((short) 0);
            }

            if (keyPairs[currentKeyPair].key.keyKype == PSBT_GLOBAL_TX_VERSION) {
                PSBTversion = keyPairs[currentKeyPair].value.getByte((short) 0);
            }

            // TODO maybe add more special key types here later on

            map_size += keyPairs[currentKeyPair].getSize();
        }
    }

        public void reset() {
            short i = 0;
            while (i < NUM_OF_KEYPAIR) {
                keyPairs[i].reset();
                i++;
            }
            input_maps_total = -1;
            outputMapsTotal = -1;
            PSBTversion = 0;
            globalUnsignedTX.reset();
            map_start = -1;
            currentKeyPair = -1;
            map_size = 0;
    }
}
