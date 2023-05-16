package applet;

import static applet.AppletInstructions.*;
import static applet.MainApplet.PSBTdata;

public class GlobalMap extends GeneralMap {
    short inputMapsTotal = -1;
    short outputMapsTotal = -1;
    static short PSBTversion = 0;
    GlobalUnsignedTX globalUnsignedTX = new GlobalUnsignedTX();

    public void fill(short arrayIndex) {
        mapStart = (short) (arrayIndex + 1);
        while ((PSBTdata[(short) (arrayIndex + mapSize)] & 0xff) != 0x00 && currentKeyPair < (short) (NUM_OF_KEYPAIR - 1)) {
            currentKeyPair++;
            keyPairs[currentKeyPair].fill((short) (arrayIndex + mapSize));

            if (keyPairs[currentKeyPair].key.keyKype == PSBT_GLOBAL_UNSIGNED_TX) {
                globalUnsignedTX.fill((short) (arrayIndex + mapSize + 2 +
                        keyPairs[currentKeyPair].value.valueLenBytes));
            }

            if (keyPairs[currentKeyPair].key.keyKype == PSBT_GLOBAL_INPUT_COUNT) {
                inputMapsTotal = keyPairs[currentKeyPair].value.getByte((short) 0);
            }

            if (keyPairs[currentKeyPair].key.keyKype == PSBT_GLOBAL_OUTPUT_COUNT) {
                outputMapsTotal = keyPairs[currentKeyPair].value.getByte((short) 0);
            }

            if (keyPairs[currentKeyPair].key.keyKype == PSBT_GLOBAL_TX_VERSION) {
                PSBTversion = keyPairs[currentKeyPair].value.getByte((short) 0);
            }

            mapSize += keyPairs[currentKeyPair].getSize();
        }
    }

        public void reset() {
            short i = 0;
            while (i < NUM_OF_KEYPAIR) {
                keyPairs[i].reset();
                i++;
            }
            inputMapsTotal = -1;
            outputMapsTotal = -1;
            PSBTversion = 0;
            globalUnsignedTX.reset();
            mapStart = -1;
            currentKeyPair = -1;
            mapSize = 0;
    }
}
