package applet;

import static applet.Tools.byteSizeOfCWI;
import static applet.Tools.compactWeirdoInt;

public class GlobalUnsignedTXOutput {
    short valueStart = -1;
    short scriptSizeStart = -1;
    short scriptSize = -1;
    short scriptPubKeyStart = -1;
    short size = 0;

    void fill(short start){
        valueStart = start; // value has static size of 8 bytes
        scriptSizeStart = (short) (start + 8);
        scriptSize = compactWeirdoInt(scriptSizeStart);
        scriptPubKeyStart = (short) (scriptSizeStart + byteSizeOfCWI(scriptSize));
        size = (short) (8 + byteSizeOfCWI(scriptSize) + scriptSize); // easier to read and understand this way
    }

    void reset() {
        valueStart = -1;
        scriptSizeStart = -1;
        scriptSize = -1;
        scriptPubKeyStart = -1;
        size = 0;
    }
}
