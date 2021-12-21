package applet;

public class PSBT {
    short MAX_OF_IO_MAPS = 256;
    public GlobalMap globalMap = new GlobalMap();
    public GeneralMap[] inputMaps = new GeneralMap[MAX_OF_IO_MAPS];
    public GeneralMap[] outputMaps = new GeneralMap[MAX_OF_IO_MAPS];

    public short currentInputMap = 0;
    public short currentOutputMap = 0;

    short byteSize = 0;

    public PSBT() {
        short i = 0;
        while (i < MAX_OF_IO_MAPS) {
            inputMaps[i] = new GeneralMap();
            outputMaps[i] = new GeneralMap();
            i++;
        }
    }

    public void fillUp(byte[] data) throws Exception {
        /**
         assert data[0] == 0x70;
         assert data[1] == 0x73;
         assert data[2] == 0x62;
         assert data[3] == 0x74;
         assert (data[4] & 0xff) == 0xff;
         TODO: delete later
         **/

         if ((data[0] == 0x70) && (data[1] == 0x73) && (data[2] == 0x62) && (data[3] == 0x74) && (data[4] == (data[4] & 0xff))){
            throw new Exception("Unsupported (black and forbidden) magic!");
         }

        System.out.print("magic is ok" + System.lineSeparator());

        byteSize += 5;

        globalMap.fillUp((short) 5, data);

        byteSize += globalMap.mapSize;

        System.out.print(data[byteSize] + System.lineSeparator());

        assert data[byteSize] == 0x00;
        byteSize++;

        System.out.print("global map successfully filled" + System.lineSeparator());

        //TODO: check that current IOMap != numOfIOMaps

        while (globalMap.numOfInputMaps != null && currentInputMap < globalMap.numOfInputMaps) {
            inputMaps[currentInputMap].fillUp(byteSize, data);
            byteSize += inputMaps[currentInputMap].mapSize;
            assert data[byteSize] == 0x00;
            System.out.print("input map with index " + currentInputMap + "successfully filled" + System.lineSeparator());
            byteSize++;
            currentInputMap++;
        }

        while (globalMap.numOfOutputMaps != null && currentOutputMap < globalMap.numOfOutputMaps) {
            outputMaps[currentOutputMap].fillUp(byteSize, data);
            byteSize += outputMaps[currentOutputMap].mapSize;
            assert data[byteSize] == 0x00;
            System.out.print("output map with index " + currentOutputMap + "successfully filled" + System.lineSeparator());
            byteSize++;
            currentOutputMap++;
        }

    }
}
