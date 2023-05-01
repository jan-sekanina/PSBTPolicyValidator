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

    short input_count = -1;
    GlobalUnsignedTXInput[] inputs = new GlobalUnsignedTXInput[MAX_COUNT_OF_IO];

    short output_count = -1;
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
        version = (short) PSBTdata[start];
        size += 4;
        input_count = getCount();
        size += byteSizeOfCWI(input_count);


        for (short i = 0; i < input_count; i++) {
            inputs[i].fill((short) (start + size));
            size += inputs[i].size;
        }

        output_count = getCount();
        size += byteSizeOfCWI(output_count);

        for (short i = 0; i < output_count; i++) {
            outputs[i].fill((short)(start + size));
            size += outputs[i].size;
        }
        lockTimeStart = size;
        size += 4;
    }
    short getCount() {
        return compactWeirdoInt((short) (start + size));
    }

    short ignoreInput(short bytesIgnored) {
        short signature_scrip_size = compactWeirdoInt((short) (bytesIgnored + 36));
        return (short) (signature_scrip_size + 40 + byteSizeOfCWI(signature_scrip_size));
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
