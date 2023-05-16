package applet;

import static applet.Tools.byteSizeOfCWI;
import static applet.Tools.compactWeirdoInt;

public class GlobalUnsignedTXInput {
    short previousOutputStart = -1;
    short scriptSizeStart = -1;
    short scriptSize = -1;
    short scriptSigStart = -1;
    short sequenceStart = -1;
    short size = 0;

    void fill(short start){
        previousOutputStart = start;
        scriptSizeStart = (short) (start + 36);
        scriptSize = compactWeirdoInt(scriptSizeStart);
        scriptSigStart = (short) (scriptSizeStart + byteSizeOfCWI(scriptSize));
        sequenceStart = (short) (scriptSigStart + scriptSize);
        size = (short) (36 + byteSizeOfCWI(scriptSize) + scriptSize + 4); // easier to read and understand this way
    }

    void reset(){
        previousOutputStart = -1;
        scriptSizeStart = -1;
        scriptSize = -1;
        scriptSigStart = -1;
        sequenceStart = -1;
        size = 0;
    }
}
