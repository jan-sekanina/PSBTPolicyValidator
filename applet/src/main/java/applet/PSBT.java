package applet;

import static applet.MainApplet.PSBTdata;

public class PSBT {
    public static final short MAX_OF_IO_MAPS = 8;
    public GlobalMap globalMap = new GlobalMap();
    public GeneralMap[] inputMaps = new GeneralMap[MAX_OF_IO_MAPS];
    public GeneralMap[] outputMaps = new GeneralMap[MAX_OF_IO_MAPS];


    public static short currentInputMap = 0; // how many input maps are filled
    public static short currentOutputMap = 0; // above for output

    static short byteSize = 0;

    public PSBT() {
        for (short i = 0; i < MAX_OF_IO_MAPS; i++) {
            inputMaps[i] = new GeneralMap();
            outputMaps[i] = new GeneralMap();
        }
    }

    public void fill() throws Exception {
         if (((PSBTdata[0] & 0xff) != 0x70) ||
                 ((PSBTdata[1] & 0xff) != 0x73) ||
                 ((PSBTdata[2] & 0xff) != 0x62) ||
                 ((PSBTdata[3] & 0xff) != 0x74) ||
                 ((PSBTdata[4] & 0xff) != 0xff)) {
	     return;
         }

        byteSize += (short) 5;

        globalMap.fill((short) 5);

        byteSize += globalMap.mapSize;

        byteSize++;

        while (currentInputMap < globalMap.inputMapsTotal) {
            inputMaps[currentInputMap].fill(byteSize);
            byteSize += inputMaps[currentInputMap].mapSize;
            byteSize++;
            currentInputMap++;
        }

        while (currentOutputMap < globalMap.outputMapsTotal) {
            outputMaps[currentOutputMap].fill(byteSize);
            byteSize += outputMaps[currentOutputMap].mapSize;
            byteSize++;
            currentOutputMap++;
        }
        Tools.getTotalOutput();
    }

    public void reset() {
        short i = 0;
        globalMap.reset();
        while (i < MAX_OF_IO_MAPS) {
            inputMaps[i].reset();
            outputMaps[i].reset();
            i++;
        }

        i = 0;
        while (i < 8) {
            MainApplet.totalOutput[i] = 0;
            i++;
        }

        currentInputMap = 0;
        currentOutputMap = 0;
        byteSize = 0;
    }
}
