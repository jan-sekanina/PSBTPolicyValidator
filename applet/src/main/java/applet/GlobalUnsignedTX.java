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
    Short start = null;
    Short version = null;
    short size = 0;

    Short inputCount = null;
    short currentInput = -1;
    GlobalUnsignedTXInput[] inputs = new GlobalUnsignedTXInput[MAX_COUNT_OF_IO];

    Short outputCount = null;
    short currentOutput = -1;
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
        inputCount = getInputCount();

        while (currentInput < inputCount - 1){
            currentInput++;
            inputs[currentInput].fill((short) (start + size));
            size += inputs[currentInput].size;
        }

        outputCount = getOutputCount();
        size += byteSizeOfCWI(outputCount);

        while (currentOutput < outputCount - 1){
            currentOutput++;
            outputs[currentOutput].fill((short)(start + size));
            size += outputs[currentOutput].size;
        }

    }
    short getInputCount() {
        return compactWeirdoInt((short) (start + 4));
    }
    short getOutputCount() {
        return compactWeirdoInt((short) (start + size));
    }

    short ignoreInput(short bytesIgnored) {
        short signature_scrip_size = compactWeirdoInt((short) (bytesIgnored + 36));
        System.out.print("signature_script_size = " + signature_scrip_size + System.lineSeparator());
        return (short) (signature_scrip_size + 40 + byteSizeOfCWI(signature_scrip_size));
    }
}
