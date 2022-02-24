package applet;

import static applet.AppletInstructions.*;
import static applet.MainApplet.PSBTdata;

public class GlobalMap extends GeneralMap {
    Short numOfInputMaps = null;
    Short numOfOutputMaps = null;
    Short PSBTversion = null;
    //PGU_TX_keypair fancyKeyPairInfo = new PGU_TX_keypair();
    GlobalUnsignedTX globalUnsignedTX = new GlobalUnsignedTX();

    public void fillUp(short arrayIndex) {
        while ((PSBTdata[arrayIndex + mapSize] & 0xff) != 0x00 && currentKeyPair < NUM_OF_KEYPAIR - 1) {
            currentKeyPair++;
            keyPairs[currentKeyPair].fill((short) (arrayIndex + mapSize));

            if (keyPairs[currentKeyPair].key.keyType == PSBT_GLOBAL_UNSIGNED_TX) {
                assert PSBTversion == null || PSBTversion != 2;
                globalUnsignedTX.fill((short) (arrayIndex + mapSize + 2 +
                        keyPairs[currentKeyPair].value.valueLenBytes));
                assert globalUnsignedTX.size == keyPairs[currentKeyPair].value.valueLen;
            }

            if (keyPairs[currentKeyPair].key.keyType == PSBT_GLOBAL_INPUT_COUNT) {
                numOfInputMaps = (short) keyPairs[currentKeyPair].value.getByte((short) 0);
            }

            if (keyPairs[currentKeyPair].key.keyType == PSBT_GLOBAL_OUTPUT_COUNT) {
                numOfOutputMaps = (short) keyPairs[currentKeyPair].value.getByte((short) 0);
            }

            if (keyPairs[currentKeyPair].key.keyType == PSBT_GLOBAL_TX_VERSION) {
                PSBTversion = (short) keyPairs[currentKeyPair].value.getByte((short) 3);
                assert PSBTversion != 2 || globalUnsignedTX.start == null;
            }

            // TODO maybe add more special key types here later on

            mapSize += keyPairs[currentKeyPair].getSize();
        }
        //System.out.print("numOfInputMap in fancyKeyPair: " + fancyKeyPairInfo.inputCount + System.lineSeparator());
        //System.out.print("numOfOutputMap in fancyKeyPair: " + fancyKeyPairInfo.outputCount + System.lineSeparator());
        System.out.print("numOfInputMap: " + numOfInputMaps + System.lineSeparator());
        System.out.print("numOfOutputMaps: " + numOfOutputMaps + System.lineSeparator());
    }
}
