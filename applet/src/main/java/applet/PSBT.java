package applet;

import static applet.MainApplet.PSBTdata;

public class PSBT {
    public static final short MAX_OF_IO_MAPS = 8;
    public GlobalMap globalMap = new GlobalMap();
    public GeneralMap[] inputMaps = new GeneralMap[MAX_OF_IO_MAPS];
    public GeneralMap[] outputMaps = new GeneralMap[MAX_OF_IO_MAPS];


    public short currentInputMap = 0; // how many input maps are filled
    public short currentOutputMap = 0; // above for output

    short byteSize = 0;

    public PSBT() {
        for (short i = 0; i < MAX_OF_IO_MAPS; i++){
            inputMaps[i] = new GeneralMap();
            outputMaps[i] = new GeneralMap();
        }
    }

    public void fill() throws Exception {
        /**
         assert data[0] == 0x70;
         assert data[1] == 0x73;
         assert data[2] == 0x62;
         assert data[3] == 0x74;
         assert (data[4] & 0xff) == 0xff;
         TODO: delete later
         **/

         if (((PSBTdata[0] & 0xff) != 0x70) ||
                 ((PSBTdata[1] & 0xff) != 0x73) ||
                 ((PSBTdata[2] & 0xff) != 0x62) ||
                 ((PSBTdata[3] & 0xff) != 0x74) ||
                 ((PSBTdata[4] & 0xff) != 0xff)) {
            throw new Exception("Unsupported (black and forbidden) magic!");
         }

        System.out.print("magic is ok" + System.lineSeparator());
        byteSize += 5;

        globalMap.fillUp((short) 5);

        byteSize += globalMap.mapSize;

        assert PSBTdata[byteSize] == 0x00;
        byteSize++;

        System.out.print("global map successfully filled" + System.lineSeparator());

        //TODO: check that current IOMap != numOfIOMaps

        while (globalMap.numOfInputMaps != null && currentInputMap < globalMap.numOfInputMaps) {
            inputMaps[currentInputMap].fillUp(byteSize);
            byteSize += inputMaps[currentInputMap].mapSize;
            assert PSBTdata[byteSize] == 0x00;
            System.out.print("input map with index " + currentInputMap + "successfully filled" + System.lineSeparator());
            byteSize++;
            currentInputMap++;
        }

        while (globalMap.numOfOutputMaps != null && currentOutputMap < globalMap.numOfOutputMaps) {
            outputMaps[currentOutputMap].fillUp(byteSize);
            byteSize += outputMaps[currentOutputMap].mapSize;
            assert PSBTdata[byteSize] == 0x00;
            System.out.print("output map with index " + currentOutputMap + "successfully filled" + System.lineSeparator());
            byteSize++;
            currentOutputMap++;
        }

    }
}
