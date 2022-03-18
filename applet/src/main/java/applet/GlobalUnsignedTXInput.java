package applet;

import static applet.Tools.byteSizeOfCWI;
import static applet.Tools.compactWeirdoInt;

public class GlobalUnsignedTXInput {
    Short previousOutputStart = null;
    Short scriptSizeStart = null;
    Short scriptSize = null;
    Short scriptSigStart = null;
    Short sequenceStart = null;
    short size = 0;

    void fill(short start){
        previousOutputStart = start;
        scriptSizeStart = (short) (start + 36);
        scriptSize = compactWeirdoInt(scriptSizeStart);
        scriptSigStart = (short) (scriptSizeStart + byteSizeOfCWI(scriptSize));
        sequenceStart = (short) (scriptSigStart + scriptSize);
        size = (short) (36 + byteSizeOfCWI(scriptSize) + scriptSize + 4); // easier to read and understand this way
    }

    void print() {
        System.out.print("previousOutputStart: " + previousOutputStart + System.lineSeparator());
        System.out.print("scriptSizeStart: " + scriptSigStart + System.lineSeparator());
        System.out.print("scriptSize: " + scriptSize + System.lineSeparator());
        System.out.print("scriptSigStart: " + scriptSigStart + System.lineSeparator());
        System.out.print("sequenceStart: " + sequenceStart + System.lineSeparator());
        System.out.print("size: " + size + System.lineSeparator());
    }
}
