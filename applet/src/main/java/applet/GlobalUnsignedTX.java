package applet;

import static applet.MainApplet.PSBTdata;
import static applet.MainApplet.controlArray;
import static applet.Tools.byteSizeOfCWI;
import static applet.Tools.compactWeirdoInt;

/**
 * PSBT transaction version 0 must include this
 * more documentation below:
 * https://medium.datadriveninvestor.com/bitcoin-raw-transaction-breakdown-c0a5a3aa8688
 */
public class GlobalUnsignedTX {
    final static short MAX_COUNT_OF_IO = 8;
    Short start = null;
    Short version = null;
    short size = 0;

    Short input_count = null;
    GlobalUnsignedTXInput[] inputs = new GlobalUnsignedTXInput[MAX_COUNT_OF_IO];

    Short output_count = null;
    GlobalUnsignedTXOutput[] outputs = new GlobalUnsignedTXOutput[MAX_COUNT_OF_IO];

    Short lockTimeStart = null;

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
        controlArray[0] = (byte) getCount();
        size += byteSizeOfCWI(input_count);

        assert (input_count) <= MAX_COUNT_OF_IO;

        for (short i = 0; i < input_count; i++) {
            inputs[i].fill((short) (start + size));
            size += inputs[i].size;
        }

        output_count = getCount();
        controlArray[1] = (byte) getCount();
        assert output_count <= MAX_COUNT_OF_IO;
        size += byteSizeOfCWI(output_count);

        for (short i = 0; i < output_count; i++) {
            outputs[i].fill((short)(start + size));
            size += outputs[i].size;
        }
        lockTimeStart = size;
        size += 4;
        print();
    }
    short getCount() {
        return compactWeirdoInt((short) (start + size));
    }

    void print(){
        System.out.print(("RAW TRANSACTION:" + System.lineSeparator()));
        System.out.print("starts at: " + start + System.lineSeparator());
        System.out.print("version: " + version + System.lineSeparator());
        System.out.print("size: " + size + System.lineSeparator());
        System.out.print("input count: " + input_count + System.lineSeparator());
        System.out.print("output count: " + output_count + System.lineSeparator());
        System.out.print("lockTimeStart: " + lockTimeStart + System.lineSeparator());
    }

    short ignoreInput(short bytesIgnored) {
        short signature_scrip_size = compactWeirdoInt((short) (bytesIgnored + 36));
        System.out.print("signature_script_size = " + signature_scrip_size + System.lineSeparator());
        return (short) (signature_scrip_size + 40 + byteSizeOfCWI(signature_scrip_size));
    }
}
