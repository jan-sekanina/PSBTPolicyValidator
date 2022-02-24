package tests;

import applet.*;

public class MyTests {

    MyUpload test = new MyUpload();

    byte[] fromHex(String hex){
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
        assert key.keyLen == 5;
        assert key.keyType == 10;
    }

    void valueFillTest() {
        byte[] data = fromHex("02" + "0000");
        Value value = new Value();
        value.fill((short) 0);
        assert value.valueLen == 2;
    }

    void keyPairFillTest() {
        byte[] data = fromHex("05" + "0a" + "11111111" + "02" + "0000");
        KeyPair keyPair = new KeyPair();
        keyPair.fill((short) 0);
        assert keyPair.key.keyLen == 5;
        assert keyPair.key.keyType == 10;
        assert keyPair.value.valueLen == 2;
    }

    void generalFillMapTest() {
        byte[] data = fromHex("05" + "0a" + "11111111" + "02" + "0000"+ "00" +
                "05" + "0a" + "11111111" + "02" + "0000"+ "00");
        GeneralMap map = new GeneralMap();
        map.fillUp((short) 0);
        assert map.keyPairs[0].key.keyLen == 5;
        assert map.keyPairs[0].key.keyType == 10;
        assert map.keyPairs[0].value.valueLen == 2;
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
        MyUpload mu = new MyUpload();
        mu.sendData(data, (byte) AppletInstructions.CLASS_PSBT_UPLOAD);
    }
}
