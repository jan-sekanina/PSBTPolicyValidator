package applet;

import static applet.MainApplet.*;

public class Tools {

    /**
     * function to transform two bytes in little endian short
     * @param zero zeroth(lol) byte
     * @param first firsth byte
     * @return
     */

    public static short toShort(byte zero, byte first) {
        return (short) ((zero & 0xff) * 256 + (first & 0xff)); // TODO: have to check overflow
    }


    /**
     * compactSizeInt encoded in Little Endian of max size short
     * more documentation here:
     * https://en.bitcoin.it/wiki/Protocol_documentation#Variable_length_integer
     * @param int_start index in psbt data from where get the Int
     * @return converted short
     */
    public static short compactWeirdoInt(short int_start) {
        if ((short) (PSBTdata[int_start] & 0xff) == 0xfd) {
            return (Tools.toShort(PSBTdata[(short) (int_start + 1)], PSBTdata[(short) (int_start + 2)]));
        }
        return (short) (PSBTdata[int_start] & 0xff);
    }

    public static short byteSizeOfCWI(short CWI) {
        if (CWI < 0xfd) {
            return 1;
        }
        return 3;
    }

    public static short littleEndianCompareArrays(byte[] ar1, short start1, byte[] ar2, short start2, short length) {
        short i = 1;
        while (i <= length) { // this could be simplified
            if ((ar1[(short) (start1 + length - i)] & 0xFF) > (ar2[(short) (start2 + length - i)] & 0xFF)) { return 1; }
            if ((ar1[(short) (start1 + length - i)] & 0xFF) < (ar2[(short) (start2 + length - i)] & 0xFF)) { return -1; }
            i++;
        }
        return 0;
    }
    public static void getTotalOutput() {
        short i = 0;
        if (GlobalMap.PSBTversion == 0) {
            while (i < MainApplet.psbt.global_map.globalUnsignedTX.output_count) {
                satoshisPlusLE(totalOutput, PSBTdata, MainApplet.psbt.global_map.globalUnsignedTX.outputs[i].value_start);
                i++;
            }
        }
        if (GlobalMap.PSBTversion == 2) {
            short keytype;
            while (i < MainApplet.psbt.global_map.output_maps_total) {
                short key_pair = 0;
                while (key_pair < MainApplet.psbt.output_maps[i].current_key_pair) {
                    keytype = MainApplet.psbt.output_maps[i].key_pairs[key_pair].key.key_type;
                    if (keytype == AppletInstructions.PSBT_OUT_AMOUNT) {
                        satoshisPlusLE(totalOutput, PSBTdata, MainApplet.psbt.output_maps[i].key_pairs[key_pair].value.start);
                    }
                    key_pair++;
                }
                i++;
            }
        }
    }

    public static void satoshisPlusLE(byte[] res, byte[] plusArray, short startArray){ // my future self, keep in mind this is very targeted function
        short i = 7;
        while (i >= 0) {
            if ((short) (((res[i] & 0xff) + (plusArray[(short) (startArray + i)] & 0xff))) > 0xff) {
                res[i] = (byte) ((res[i] & 0xff) + (plusArray[(short) (startArray + i)] & 0xff));
                res[i - 1]++; // this should not overflow considering maxSatoshis = "000040075AF07507";
            } else {
                res[i] = (byte) ((res[i] & 0xff) + (plusArray[(short) (startArray + i)] & 0xff));
            }
            i--;
        }
    }
}
