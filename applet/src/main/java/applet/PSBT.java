package applet;

import java.nio.charset.StandardCharsets;

public class PSBT {
    public GlobalMap globalMap = new GlobalMap();
    public GeneralMap[] inputMaps = new GeneralMap[8];
    public GeneralMap[] outputMaps = new GeneralMap[8];

    public short currentInputMap = -1;
    public short currentOutputMap = -1;

    short byteSize = 0;

    public PSBT() {
        short i = 0;
        while (i < 8) {
            inputMaps[i] = new GeneralMap();
            outputMaps[i] = new GeneralMap();
            i++;
        }
    }

    public void fillUp(byte[] data) {
         assert data[0] == 0x70;
         assert data[1] == 0x73;
         assert data[2] == 0x62;
         assert data[3] == 0x74;
         assert (data[4] & 0xff) == 0xff;

        System.out.print("magic is ok" + System.lineSeparator());

        /**
         if ((data[0] == 0x70) && (data[1] == 0x73) && (data[2] == 0x62) && (data[3] == 0x74) && (data[4] == (byte) 0xff)){
         throw new Exception("Unsupported (black and forbidden) magic!");
         }
         **/
        byteSize += 5;

        globalMap.fillUp((short) 5, data);
        byteSize += globalMap.mapSize;

        System.out.print(data[byteSize] + System.lineSeparator());
        assert data[byteSize] == 0x00;
        System.out.print("global map successfully filled" + System.lineSeparator());
        byteSize++;

        while (currentInputMap < globalMap.numOfInputMaps) {
            currentInputMap++;
            inputMaps[currentInputMap].fillUp(byteSize, data);
            byteSize += inputMaps[currentInputMap].mapSize;
            assert data[byteSize] == 0x00;
            System.out.print("input map with index " + currentInputMap + "successfully filled" + System.lineSeparator());
            byteSize++;
        }

        while (currentOutputMap < globalMap.numOfOutputMaps) {
            currentOutputMap++;
            outputMaps[currentOutputMap].fillUp(byteSize, data);
            byteSize += outputMaps[currentOutputMap].mapSize;
            assert data[byteSize] == 0x00;
            System.out.print("output map with index " + currentOutputMap + "successfully filled" + System.lineSeparator());
            byteSize++;
        }

    }
}
