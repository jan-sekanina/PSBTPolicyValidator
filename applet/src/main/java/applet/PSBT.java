package applet;

import static applet.MainApplet.PSBTdata;

public class PSBT {
    public static final short MAX_OF_IO_MAPS = 8;
    public GlobalMap global_map = new GlobalMap();
    public GeneralMap[] input_maps = new GeneralMap[MAX_OF_IO_MAPS];
    public GeneralMap[] output_maps = new GeneralMap[MAX_OF_IO_MAPS];


    public short current_input_map = 0; // how many input maps are filled
    public short current_output_map = 0; // above for output

    short byteSize = 0;

    public PSBT() {
        for (short i = 0; i < MAX_OF_IO_MAPS; i++){
            input_maps[i] = new GeneralMap();
            output_maps[i] = new GeneralMap();
        }
    }

    public void fill() throws Exception {
        /**
         assert data[0] == 0x70;
         assert data[1] == 0x73;
         assert data[2] == 0x62;
         assert data[3] == 0x74;
         assert (data[4] & 0xff) == 0xff;
         TODO: delete later
         **/

         if (((PSBTdata[0] & 0xff) != 0x70) ||
                 ((PSBTdata[1] & 0xff) != 0x73) ||
                 ((PSBTdata[2] & 0xff) != 0x62) ||
                 ((PSBTdata[3] & 0xff) != 0x74) ||
                 ((PSBTdata[4] & 0xff) != 0xff)) {
            throw new Exception("Unsupported (black and forbidden) magic!");
         }

        System.out.print("magic is ok" + System.lineSeparator());
        byteSize += 5;

        global_map.fillUp((short) 5);

        byteSize += global_map.map_size;

        assert PSBTdata[byteSize] == 0x00;
        byteSize++;

        System.out.print("global map successfully filled" + System.lineSeparator());

        //TODO: check that current IOMap != numOfIOMaps

        while (global_map.input_maps_total != null && current_input_map < global_map.input_maps_total) {
            input_maps[current_input_map].fillUp(byteSize);
            byteSize += input_maps[current_input_map].map_size;
            assert PSBTdata[byteSize] == 0x00;
            System.out.print("input map with index " + current_input_map + "successfully filled" + System.lineSeparator());
            byteSize++;
            current_input_map++;
        }

        while (global_map.output_maps_total != null && current_output_map < global_map.output_maps_total) {
            output_maps[current_output_map].fillUp(byteSize);
            byteSize += output_maps[current_output_map].map_size;
            assert PSBTdata[byteSize] == 0x00;
            System.out.print("output map with index " + current_output_map + "successfully filled" + System.lineSeparator());
            byteSize++;
            current_output_map++;
        }

    }
}
