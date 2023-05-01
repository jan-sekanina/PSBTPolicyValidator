package applet;

import static applet.MainApplet.PSBTdata;

public class PSBT {
    public static final short MAX_OF_IO_MAPS = 8;
    public GlobalMap globalMap = new GlobalMap();
    public GeneralMap[] input_maps = new GeneralMap[MAX_OF_IO_MAPS];
    public GeneralMap[] outputMaps = new GeneralMap[MAX_OF_IO_MAPS];


    public static short current_input_map = 0; // how many input maps are filled
    public static short current_output_map = 0; // above for output

    static short byte_size = 0;

    public PSBT() {
        for (short i = 0; i < MAX_OF_IO_MAPS; i++){
            input_maps[i] = new GeneralMap();
            outputMaps[i] = new GeneralMap();
        }
    }

    public void fill() throws Exception {
         if (((PSBTdata[0] & 0xff) != 0x70) ||
                 ((PSBTdata[1] & 0xff) != 0x73) ||
                 ((PSBTdata[2] & 0xff) != 0x62) ||
                 ((PSBTdata[3] & 0xff) != 0x74) ||
                 ((PSBTdata[4] & 0xff) != 0xff)) {
	     return;
         }

        byte_size += (short) 5;

        globalMap.fill((short) 5);

        byte_size += globalMap.map_size;

        byte_size++;

        while (current_input_map < globalMap.input_maps_total) {
            input_maps[current_input_map].fill(byte_size);
            byte_size += input_maps[current_input_map].map_size;
            byte_size++;
            current_input_map++;
        }

        while (current_output_map < globalMap.outputMapsTotal) {
            outputMaps[current_output_map].fill(byte_size);
            byte_size += outputMaps[current_output_map].map_size;
            byte_size++;
            current_output_map++;
        }
        Tools.getTotalOutput();
    }

    public void reset() {
        short i = 0;
        globalMap.reset();
        while (i < MAX_OF_IO_MAPS) {
            input_maps[i].reset();
            outputMaps[i].reset();
            i++;
        }

        i = 0;
        while (i < 8) {
            MainApplet.totalOutput[i] = 0;
            i++;
        }

        current_input_map = 0;
        current_output_map = 0;
        byte_size = 0;
    }
}
