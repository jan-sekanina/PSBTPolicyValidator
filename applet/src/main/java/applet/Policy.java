package applet;
import javacard.framework.Util;

import java.util.Arrays;

public class Policy {
    public static final byte minTotalOutput = 0;
    public static final byte maxTotalOutput = 1;
    public static final byte minOutputWithSigScr = 2;
    public static final byte maxOutputWithSigScr = 3;
    public static final byte minNumberofInputs = 4;
    public static final byte maxNumberofInputs = 5;
    public static final byte minNumberofOutputs = 6;
    public static final byte maxNumberofOutputs = 7;
    public static final byte naiveTimeLapsed = 8;
    public static final byte signedTmeLapse = 9;
    public static final byte checkSecret = 10;
    public static final byte transactionVersion = 12;
    public static final byte policyAnd = 13;

    public static boolean validateTotalOutput(byte storageValue, short notRes) {
        return ((Tools.littleEndianCompareArrays(MainApplet.additionalDataStorage[storageValue], (short) 0, MainApplet.totalOutput,
                (short) 0, (short) 8)) != notRes); // notRes: min = 1, max = -1
    }

    public static boolean validateValueOwSS(byte storageValue, byte storageSigScr, short notRes) {
        short sigScrStart = 0;
        short valueStart = 0;
        if (GlobalMap.PSBTversion == 0) {
            short i = 0;
            while (i < MainApplet.psbt.global_map.globalUnsignedTX.output_count) {
                valueStart = MainApplet.psbt.global_map.globalUnsignedTX.outputs[i].value_start;
                sigScrStart = MainApplet.psbt.global_map.globalUnsignedTX.outputs[i].script_pub_key_start;
                if (checkItself(storageValue, valueStart, storageSigScr, sigScrStart, notRes)) {
                    return true;
                }
                i++;
            }
        }

        if (GlobalMap.PSBTversion == 2) {
            short i = 0;
            short keytype;
            while (i < MainApplet.psbt.global_map.output_maps_total) {
                short key_pair = 0;
                while (key_pair < MainApplet.psbt.output_maps[i].current_key_pair) {
                    keytype = MainApplet.psbt.output_maps[i].key_pairs[key_pair].key.key_type;
                    if (keytype == AppletInstructions.PSBT_OUT_AMOUNT) {
                        valueStart = MainApplet.psbt.output_maps[i].key_pairs[key_pair].value.start;
                    }
                    if (keytype == AppletInstructions.PSBT_OUT_SCRIPT) {
                        sigScrStart = MainApplet.psbt.output_maps[i].key_pairs[key_pair].value.start;
                    }
                    key_pair++;
                }
                if (checkItself(storageValue, valueStart, storageSigScr, sigScrStart, notRes)) {
                    return true;
                }
                i++;
            }
        }
        return false;
    }

    static boolean checkItself(byte storageValue, short valueStart, byte storageScrSig, short sigScrStart, short notRes) {
        return ((Tools.littleEndianCompareArrays(MainApplet.additionalDataStorage[storageValue], (short) 0, MainApplet.PSBTdata,
                    valueStart, (short) (MainApplet.dataStorageOffsets[storageValue] & 0xFF)) != notRes) // min = 1, max = -1
                &&
                (Util.arrayCompare(MainApplet.additionalDataStorage[storageScrSig], (short) 0,
                        MainApplet.PSBTdata, sigScrStart, (short) 8) == 0));
    }
}
