package applet;

import static applet.Tools.byteSizeOfCWI;
import static applet.Tools.compactWeirdoInt;

public class GlobalUnsignedTXOutput {
    Short valueStart = null;
    Short scriptSizeStart = null;
    Short scriptSize = null;
    Short scriptPubKeyStart = null;
    short size = 0;

    void fill(short start){
        valueStart = start;
        scriptSizeStart = (short) (start + 8);
        scriptSize = compactWeirdoInt(scriptSizeStart);
        scriptPubKeyStart = (short) (scriptSizeStart + byteSizeOfCWI(scriptSize));
        size = (short) (8 + byteSizeOfCWI(scriptSize) + scriptSize); // easier to read and understand this way
    }
}
