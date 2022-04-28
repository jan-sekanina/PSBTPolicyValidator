package tests;

import applet.*;
import cz.muni.fi.crocs.rcard.client.CardManager;
import applet.AppletInstructions.*;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

public class MyTests {

    Upload test = new Upload();

    static byte[] fromHex(String hex){
        // ukradeno
        byte[] res = new byte[hex.length() / 2];

        for (int i = 0; i < res.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            res[i] = (byte) j;
        }

        return res;
    }
    void keyFillTest() {
        byte[] data = fromHex("05" + "0a" + "11111111");
        Key key = new Key();
        key.fill((short) 0);
        assert key.key_len == 5;
        assert key.key_type == 10;
    }

    void valueFillTest() {
        byte[] data = fromHex("02" + "0000");
        Value value = new Value();
        value.fill((short) 0);
        assert value.value_len == 2;
    }

    void keyPairFillTest() {
        byte[] data = fromHex("05" + "0a" + "11111111" + "02" + "0000");
        KeyPair keyPair = new KeyPair();
        keyPair.fill((short) 0);
        assert keyPair.key.key_len == 5;
        assert keyPair.key.key_type == 10;
        assert keyPair.value.value_len == 2;
    }

    void generalFillMapTest() {
        byte[] data = fromHex("05" + "0a" + "11111111" + "02" + "0000"+ "00" +
                "05" + "0a" + "11111111" + "02" + "0000"+ "00");
        GeneralMap map = new GeneralMap();
        map.fillUp((short) 0);
        assert map.key_pairs[0].key.key_len == 5;
        assert map.key_pairs[0].key.key_type == 10;
        assert map.key_pairs[0].value.value_len == 2;
    }

    void psbtFillTest() throws Exception {
        PSBT psbt = new PSBT();
        //psbt.fill(fromHex(TransactionsImported.validTransaction1));
    }

    void psbtFillSimpleTest() throws Exception {
        PSBT psbt = new PSBT();

        byte[] data = fromHex("1122334455" + "05" + "0a" + "05060709" + "02" + "0000"+ "05" + "0a" + "05060709" + "02" + "0000" +
                "05" + "0a" + "01020304" + "02" + "0000"+ "00");
        //psbt.fill(data);
    }

    void sendDataTest(byte[] data) throws Exception {
        Upload mu = new Upload();
        byte[] res = mu.sendData(data, (byte) AppletInstructions.CLASS_PSBT_UPLOAD);

    }
}
