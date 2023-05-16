package applet;

import static applet.MainApplet.PSBTdata;
import static applet.Tools.byteSizeOfCWI;
import static applet.Tools.compactWeirdoInt;

/**
 * PSBT transaction version 0 must include this
 * more documentation below:
 * https://medium.datadriveninvestor.com/bitcoin-raw-transaction-breakdown-c0a5a3aa8688
 */
public class GlobalUnsignedTX {
    final static short MAX_COUNT_OF_IO = 8;
    short start = -1;
    short version = -1;
    short size = 0;

    short inputCount = -1;
    GlobalUnsignedTXInput[] inputs = new GlobalUnsignedTXInput[MAX_COUNT_OF_IO];

    short outputCount = -1;
    GlobalUnsignedTXOutput[] outputs = new GlobalUnsignedTXOutput[MAX_COUNT_OF_IO];

    short lockTimeStart = -1;

    GlobalUnsignedTX(){
        for (short i = 0; i < MAX_COUNT_OF_IO; i++){
            inputs[i] = new GlobalUnsignedTXInput();
            outputs[i] = new GlobalUnsignedTXOutput();
        }
    }

    public void fill(short arrayIndex){
        start = arrayIndex;
        version = PSBTdata[start];
        size += 4;
        inputCount = getCount();
        size += byteSizeOfCWI(inputCount);


        for (short i = 0; i < inputCount; i++) {
            inputs[i].fill((short) (start + size));
            size += inputs[i].size;
        }

        outputCount = getCount();
        size += byteSizeOfCWI(outputCount);

        for (short i = 0; i < outputCount; i++) {
            outputs[i].fill((short)(start + size));
            size += outputs[i].size;
        }
        lockTimeStart = size;
        size += 4;
    }
    short getCount() {
        return compactWeirdoInt((short) (start + size));
    }

    void reset() {
        short i = 0;
        while (i < MAX_COUNT_OF_IO) {
            inputs[i].reset();
            outputs[i].reset();
            i++;
        }
        start = -1;
        version = -1;
        size = 0;
    }
}
