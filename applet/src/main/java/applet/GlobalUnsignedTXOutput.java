package applet;

import static applet.Tools.byteSizeOfCWI;
import static applet.Tools.compactWeirdoInt;

public class GlobalUnsignedTXOutput {
    short value_start = -1;
    short script_size_start = -1;
    short script_size = -1;
    short script_pub_key_start = -1;
    short size = 0;

    void fill(short start){
        value_start = start; // value has static size of 8 bytes
        script_size_start = (short) (start + 8);
        script_size = compactWeirdoInt(script_size_start);
        script_pub_key_start = (short) (script_size_start + byteSizeOfCWI(script_size));
        size = (short) (8 + byteSizeOfCWI(script_size) + script_size); // easier to read and understand this way
    }

    void reset() {
        value_start = -1;
        script_size_start = -1;
        script_size = -1;
        script_pub_key_start = -1;
        size = 0;
    }
}
