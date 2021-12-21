package applet;

public class GlobalMap extends GeneralMap {
    Short numOfInputMaps = null;
    Short numOfOutputMaps = null;
    PGU_TX_keypair fancyKeyPairInfo = new PGU_TX_keypair();

    public void fillUp(short arrayIndex, byte[] data) {
        while (data[arrayIndex + mapSize] != 0x00) {
            keyPairs[currentKeyPair].fillUp((short) (arrayIndex + mapSize), data);

            if (keyPairs[currentKeyPair].key.keyType == 0x00) { // PSBT_GLOBAL_UNSIGNED_TX
                fancyKeyPairInfo.fillUp((short) (arrayIndex + mapSize), data);
            }

            if (keyPairs[currentKeyPair].key.keyType == 0x04) { // PSBT_GLOBAL_INPUT_COUNT = 0x04
                numOfInputMaps = (short) keyPairs[currentKeyPair].value.getByte((short) 0);
            }

            if (keyPairs[currentKeyPair].key.keyType == 0x05) { // PSBT_GLOBAL_OUTPUT_COUNT = 0x05
                numOfOutputMaps = (short) keyPairs[currentKeyPair].value.getByte((short) 0);
            }
            mapSize += keyPairs[currentKeyPair].getSize();
            currentKeyPair++;
        }
        System.out.print("numOfInputMap in fancyKeyPair: " + fancyKeyPairInfo.inputCount + System.lineSeparator());
        System.out.print("numOfOutputMap in fancyKeyPair: " + fancyKeyPairInfo.outputCount + System.lineSeparator());
        System.out.print("numOfInputMap: " + numOfInputMaps + System.lineSeparator());
        System.out.print("numOfOutputMaps: " + numOfOutputMaps + System.lineSeparator());
    }
}
