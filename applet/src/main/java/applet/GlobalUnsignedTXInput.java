package applet;

import static applet.Tools.byteSizeOfCWI;
import static applet.Tools.compactWeirdoInt;

public class GlobalUnsignedTXInput {
    short previous_output_start = -1;
    Short script_size_start = null;
    Short script_size = null;
    Short script_sig_start = null;
    Short sequence_start = null;
    short size = 0;

    void fill(short start){
        previous_output_start = start;
        script_size_start = (short) (start + 36);
        script_size = compactWeirdoInt(script_size_start);
        script_sig_start = (short) (script_size_start + byteSizeOfCWI(script_size));
        sequence_start = (short) (script_sig_start + script_size);
        size = (short) (36 + byteSizeOfCWI(script_size) + script_size + 4); // easier to read and understand this way
    }
}
