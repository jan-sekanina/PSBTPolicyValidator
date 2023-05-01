package applet;

import static applet.MainApplet.*;

public class Tools {
    /**
     * compactSizeInt encoded in Little Endian of max size short
     * more documentation here:
     * https://en.bitcoin.it/wiki/Protocol_documentation#Variable_length_integer
     * @param intStart index in psbt data from where get the Int
     * @return converted short
     */
    public static short compactWeirdoInt(short intStart) {
        if ((short) (PSBTdata[intStart] & 0xff) == 0xfd) {
            return (short) ((PSBTdata[(short) (intStart + 1)] & 0xff) << 8 | (PSBTdata[(short) (intStart + 2)] & 0xff));
        }
        return (short) (PSBTdata[intStart] & 0xff);
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
            while (i < MainApplet.psbt.globalMap.globalUnsignedTX.output_count) {
                satoshisPlusLE(totalOutput, PSBTdata, MainApplet.psbt.globalMap.globalUnsignedTX.outputs[i].valueStart);
                i++;
            }
        }
        if (GlobalMap.PSBTversion == 2) {
            short keyType;
            while (i < MainApplet.psbt.globalMap.outputMapsTotal) {
                short key_pair = 0;
                while (key_pair < MainApplet.psbt.outputMaps[i].currentKeyPair) {
                    keyType = MainApplet.psbt.outputMaps[i].keyPairs[key_pair].key.keyKype;
                    if (keyType == AppletInstructions.PSBT_OUT_AMOUNT) {
                        satoshisPlusLE(totalOutput, PSBTdata, MainApplet.psbt.outputMaps[i].keyPairs[key_pair].value.start);
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
                res[(short) (i - 1)]++; // this should not overflow considering maxSatoshis = "000040075AF07507";
            } else {
                res[i] = (byte) ((res[i] & 0xff) + (plusArray[(short) (startArray + i)] & 0xff));
            }
            i--;
        }
    }
}
