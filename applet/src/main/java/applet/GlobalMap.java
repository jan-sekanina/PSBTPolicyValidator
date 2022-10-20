package applet;

import static applet.AppletInstructions.*;
import static applet.MainApplet.PSBTdata;

public class GlobalMap extends GeneralMap {
    Short input_maps_total = null;
    Short output_maps_total = null;
    Short PSBTversion = null;
    GlobalUnsignedTX globalUnsignedTX = new GlobalUnsignedTX();

    public void fill(short arrayIndex) {
        map_start = (short) (arrayIndex + 1);
        while ((PSBTdata[arrayIndex + map_size] & 0xff) != 0x00 && current_key_pair < NUM_OF_KEYPAIR - 1) {
            current_key_pair++;
            key_pairs[current_key_pair].fill((short) (arrayIndex + map_size));

            if (key_pairs[current_key_pair].key.key_type == PSBT_GLOBAL_UNSIGNED_TX) {
                globalUnsignedTX.fill((short) (arrayIndex + map_size + 2 +
                        key_pairs[current_key_pair].value.value_len_bytes));
            }

            if (key_pairs[current_key_pair].key.key_type == PSBT_GLOBAL_INPUT_COUNT) {
                input_maps_total = (short) key_pairs[current_key_pair].value.getByte((short) 0);
            }

            if (key_pairs[current_key_pair].key.key_type == PSBT_GLOBAL_OUTPUT_COUNT) {
                output_maps_total = (short) key_pairs[current_key_pair].value.getByte((short) 0);
            }

            if (key_pairs[current_key_pair].key.key_type == PSBT_GLOBAL_TX_VERSION) {
                PSBTversion = (short) key_pairs[current_key_pair].value.getByte((short) 3);
            }

            // TODO maybe add more special key types here later on

            map_size += key_pairs[current_key_pair].getSize();
        }
    }
}
