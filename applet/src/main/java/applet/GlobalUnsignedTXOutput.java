package applet;

import static applet.Tools.byteSizeOfCWI;
import static applet.Tools.compactWeirdoInt;

public class GlobalUnsignedTXOutput {
    short value_start = -1;
    Short script_size_tart = null;
    Short script_size = null;
    Short script_pub_key_start = null;
    short size = 0;

    void fill(short start){
        value_start = start;
        script_size_tart = (short) (start + 8);
        script_size = compactWeirdoInt(script_size_tart);
        script_pub_key_start = (short) (script_size_tart + byteSizeOfCWI(script_size));
        size = (short) (8 + byteSizeOfCWI(script_size) + script_size); // easier to read and understand this way
    }
}
