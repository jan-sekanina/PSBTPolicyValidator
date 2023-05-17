package tests;

import applet.AppletInstructions;
import communication.*;
import cz.muni.fi.crocs.rcard.client.CardManager;

import cz.muni.fi.crocs.rcard.client.CardType;
import javacard.framework.Util;
import org.junit.jupiter.api.*;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;

import static communication.SetSignAndVerify.*;
import static communication.Tools.bytesToHex;
import static communication.Tools.fromHex;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppletTest extends BaseTest {
    public AppletTest() {
        // Change card type to this here if you want to use physical card
        // setCardType(CardType.PHYSICAL);
        setCardType(CardType.JCARDSIMLOCAL);
    }

    @BeforeAll
    public static void setUpClass() throws Exception {
    }

    @AfterAll
    public static void tearDownClass() throws Exception {
    }

    @BeforeEach
    public void setUpMethod() throws Exception {
    }

    @AfterEach
    public void tearDownMethod() throws Exception {
    }

    // Example test
    @Test
    public void hello() throws Exception {
        final CommandAPDU cmd = new CommandAPDU(0x00, 0x90, 0, 0);
        final ResponseAPDU responseAPDU = connect().transmit(cmd);
        Assertions.assertNotNull(responseAPDU);
        assertEquals(0x9000, responseAPDU.getSW());
        Assertions.assertNotNull(responseAPDU.getBytes());
        System.out.print("Test hello: passed" + System.lineSeparator());
    }

    @Test
    void oneTestToRuleThemAll() throws Exception {
        // maybe TODO
    }

    @Test
    void littleEndianCompareTest() throws Exception {
        byte[] ar1 = new byte[4];
        byte[] ar2 = new byte[4];
        int i = 0;
        while (i < 4) {
            ar1[i] = 0;
            ar2[i] = 0;
            i++;
        }
        assertEquals(0, applet.Tools.littleEndianCompareArrays(ar1, (short) 0, ar2, (short) 0, (short) 4));
        ar1[0] = (byte) 1;
        assertEquals(1, applet.Tools.littleEndianCompareArrays(ar1, (short) 0, ar2, (short) 0, (short) 4));
        assertEquals(0, applet.Tools.littleEndianCompareArrays(ar1, (short) 0, ar2, (short) 0, (short) 0));
        assertEquals(0, applet.Tools.littleEndianCompareArrays(ar1, (short) 1, ar2, (short) 0, (short) 3));
        ar2[3] = (byte) 1;
        assertEquals(1, applet.Tools.littleEndianCompareArrays(ar1, (short) 0, ar2, (short) 0, (short) 3));
        ar2[0] = (byte) 2;
        assertEquals(-1, applet.Tools.littleEndianCompareArrays(ar1, (short) 0, ar2, (short) 0, (short) 3));
        ar1[3] = (byte) 2;
        assertEquals(1, applet.Tools.littleEndianCompareArrays(ar1, (short) 0, ar2, (short) 0, (short) 4));
        ar2[3] = (byte) 2;
        assertEquals(-1, applet.Tools.littleEndianCompareArrays(ar1, (short) 0, ar2, (short) 0, (short) 4));
        ar2[3] = (byte) 200;
        assertEquals(-1, applet.Tools.littleEndianCompareArrays(ar1, (short) 0, ar2, (short) 0, (short) 4));
    }

    @Test
    void simpleDeletionOfAccessibleSpaceAppletTest() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] policy = fromHex("0a000a010a020a030a040a050a060a070a080a09"); // ten secrets connected with "or" no "and" clauses in here
        byte[] secret = fromHex("00000b");
        byte[] secret2 = fromHex("11111b");
        byte[] secret3 = fromHex("22222b");
        byte[] secret4 = fromHex("33333b");
        byte[] secret5 = fromHex("44444b");
        byte[] secret6 = fromHex("55555b");
        byte[] secret7 = fromHex("66666b");
        byte[] secret8 = fromHex("77777b");
        byte[] secret9 = fromHex("88888b");
        byte[] secret10 = fromHex("99999b");
        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(secret2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);
        upload.sendData(secret3, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 2, (byte) 0, manager);
        upload.sendData(secret4, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 3, (byte) 0, manager);
        upload.sendData(secret5, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 4, (byte) 0, manager);
        upload.sendData(secret6, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 5, (byte) 0, manager);
        upload.sendData(secret7, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 6, (byte) 0, manager);
        upload.sendData(secret8, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 7, (byte) 0, manager);
        upload.sendData(secret9, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 8, (byte) 0, manager);
        upload.sendData(secret10, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 9, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        Assertions.assertEquals(0, download.downloadValidation(manager));
        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));
        Assertions.assertEquals(0, download.downloadValidation(manager));
        upload.sendData(secret2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));
        Assertions.assertEquals(0, download.downloadValidation(manager));
        upload.sendData(secret3, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 2, (byte) 0, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));
        Assertions.assertEquals(0, download.downloadValidation(manager));
        upload.sendData(secret4, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 3, (byte) 0, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));
        Assertions.assertEquals(0, download.downloadValidation(manager));
        upload.sendData(secret5, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 4, (byte) 0, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));
        Assertions.assertEquals(0, download.downloadValidation(manager));
        upload.sendData(secret6, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 5, (byte) 0, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));
        Assertions.assertEquals(0, download.downloadValidation(manager));
        upload.sendData(secret7, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 6, (byte) 0, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));
        Assertions.assertEquals(0, download.downloadValidation(manager));
        upload.sendData(secret8, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 7, (byte) 0, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));
        Assertions.assertEquals(0, download.downloadValidation(manager));
        upload.sendData(secret9, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 8, (byte) 0, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));
        Assertions.assertEquals(0, download.downloadValidation(manager));
        upload.sendData(secret10, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 9, (byte) 0, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));
        Assertions.assertEquals(0, download.downloadValidation(manager));
    }

    @Test
    void appletMaxTotalOutputsTest() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction1);
        byte[] transaction2 = fromHex(TestTransactions.validTransaction2);

        byte[] value = fromHex("ffffffffffffffff");
        byte[] policy = fromHex("0100"); // policy checks max total output

        upload.sendData(value, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager) );
    }

    @Test
    void appletMaxTotalOutputsTest2() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction1);
        byte[] transaction2 = fromHex(TestTransactions.validTransaction2);

        byte[] value = fromHex("0000000000000000");
        byte[] policy = fromHex("0100"); // policy checks max total output

        upload.sendData(value, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager) );
    }

    @Test
    void appletMinTotalOutputsTest2() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction1);
        byte[] transaction2 = fromHex(TestTransactions.validTransaction2);

        byte[] value = fromHex("0fffffffffffffff");
        byte[] policy = fromHex("0000"); // policy checks min total output

        upload.sendData(value, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager) );
        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager) );
    }

    @Test
    void PSBT2MaxTotalOutputsTest() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction10 = fromHex(TestTransactions.validTransaction10);
        byte[] transaction11 = fromHex(TestTransactions.validTransaction11);
        byte[] transaction12 = fromHex(TestTransactions.validTransaction12);

        byte[] value = fromHex("afffffffffffffff");
        byte[] policy = fromHex("0100"); // policy checks max total output

        upload.sendData(value, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction10, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager) );
        upload.sendData(transaction11, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager) );
        upload.sendData(transaction12, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager) );
    }

    @Test
    void PSBT2TwoExactOutputsWithSSTest() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction11 = fromHex(TestTransactions.validTransaction11);
        byte[] transaction12 = fromHex(TestTransactions.validTransaction12);

        byte[] value1 = fromHex("603bea0b00000000"); // value here is taken from transaction2
        byte[] scriptSig1 = fromHex("76a914768a40bbd740cbe81d988e71de2a4d5c71396b1d88ac");

        byte[] value2 = fromHex("8e24000000000000"); // value here is taken from transaction2
        byte[] scriptSig2 = fromHex("76a9146f4620b553fa095e721b9ee0efe9fa039cca459788ac");

        byte[] policy = fromHex("0200010d0300010d0202030d030203"); // policy checks exact output value with ScriptSig

        upload.sendData(value1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(scriptSig1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);
        upload.sendData(value2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 2, (byte) 0, manager);
        upload.sendData(scriptSig2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 3, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction11, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));

        upload.sendData(transaction12, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));
    }

    @Test
    void PSBT2MinTotalOutputsTest() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction10 = fromHex(TestTransactions.validTransaction10);
        byte[] transaction11 = fromHex(TestTransactions.validTransaction11);
        byte[] transaction12 = fromHex(TestTransactions.validTransaction12);

        byte[] value = fromHex("1000000000000000");
        byte[] policy = fromHex("0000"); // policy checks min total output

        upload.sendData(value, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction10, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager) );
        upload.sendData(transaction11, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager) );
        upload.sendData(transaction12, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager) );
    }

    @Test
    void appletMinTotalOutputsTest() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction1);
        byte[] transaction2 = fromHex(TestTransactions.validTransaction2);

        byte[] value = fromHex("1000000000000000");
        byte[] policy = fromHex("0000"); // policy checks min total output

        upload.sendData(value, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager) );
        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager) );
    }

    @Test
    void appletExactTotalOutputTest() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction1);
        byte[] transaction2 = fromHex(TestTransactions.validTransaction2);

        byte[] value = fromHex("ee5fea0b00000000"); // used sparrow and random online convertor from transaction2
        byte[] policy = fromHex("00000d0100"); // policy checks min total output

        upload.sendData(value, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager) );

        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager) );

    }

    @Test
    void policyNotUsingStorageTest1() throws Exception {
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] policy = fromHex("0a00"); // policy checks one secret and exactly one input and psbt version 0
        try {
            upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager, 0x8666);
        } catch (Exception e) {
            System.out.println("Something went right.");
        }
    }

    @Test
    void policyNotUsingStorageTest2() throws Exception {
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] policy = fromHex("0a000a01");
        byte[] secret = fromHex("01020304050102030405");
        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager, 0x8666);
    }

    @Test
    void appletSignedTimeTest() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] priKey = fromHex(TestKeys.sslPri1);
        byte[] pubKey = fromHex(TestKeys.sslPub1);
        byte[] signatureTMP = new byte[128];
        byte[] minUNIXtime = {(byte) 0x00, (byte) 0x0f, (byte) 0xff, (byte) 0xff};
        byte[] serverUNIXtime1 = {(byte) 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff};
        byte[] serverUNIXtime2 = {(byte) 0x00, (byte) 0x00, (byte) 0xff, (byte) 0xff};
        setPrivateKey(priKey);
        short sigLen1 = SetSignAndVerify.SignECDSA(serverUNIXtime1, signatureTMP);
        byte[] signature1 = new byte[sigLen1];
        Util.arrayCopyNonAtomic(signatureTMP, (short) 0, signature1, (short) 0, sigLen1); //  disgusting stuff I know
        short sigLen2 = SetSignAndVerify.SignECDSA(serverUNIXtime2, signatureTMP);
        byte[] signature2 = new byte[sigLen2];
        Util.arrayCopyNonAtomic(signatureTMP, (short) 0, signature2, (short) 0, sigLen2); //  disgusting stuff I know

        byte[] policy = fromHex("090001"); // policy checks signed time

        upload.sendData(pubKey, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(minUNIXtime, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(signature1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(serverUNIXtime1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);

        Assertions.assertEquals(1, download.downloadValidation(manager));

        upload.sendData(signature2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(serverUNIXtime1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));

        upload.sendData(signature2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(serverUNIXtime2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));

        upload.sendData(signature1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(serverUNIXtime2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));
    }

    @Test
    void appletSignedPSBTTest() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction1);
        byte[] transaction2 = fromHex(TestTransactions.validTransaction2);
        byte[] priKey = fromHex(TestKeys.sslPri1);
        byte[] pubKey = fromHex(TestKeys.sslPub1);
        byte[] signatureTMP = new byte[128];
        setBoth(priKey, pubKey);
        short sigLen = SetSignAndVerify.SignECDSA(transaction, signatureTMP);
        byte[] signature = new byte[sigLen];
        Util.arrayCopyNonAtomic(signatureTMP, (short) 0, signature, (short) 0, sigLen);

        byte[] policy = fromHex("0800"); // policy checks signed transaction

        upload.sendData(pubKey, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(signature, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);

        Assertions.assertEquals(0, download.downloadValidation(manager));

        upload.sendData(signature, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));

        upload.sendData(signature, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));
    }

    @Test
    void appletSimpleSignTest() {
        byte[] privateKey = fromHex(TestKeys.sslPri1);
        byte[] publicKey = fromHex(TestKeys.sslPub1);
        setPrivateKey(privateKey);
        setPublicKey(publicKey);
        //setBoth(privateKey, publicKey);
        byte[] dataToBeSigned = fromHex("55555555aabbccddeeffaabbcc");
        byte[] signature = new byte[128];
        Assertions.assertTrue(SetSignAndVerify.SignVerifyECDSA(dataToBeSigned, signature));
        short sigLen = SetSignAndVerify.SignECDSA(dataToBeSigned, signature);
        Assertions.assertTrue(SetSignAndVerify.verifyECDSA(dataToBeSigned, (short) dataToBeSigned.length, signature, sigLen));
    }

    @Test
    void appletSimpleGenTest() {
        genBoth();
        byte[] dataToBeSigned = fromHex("55555555aabbccddeeffaabbcc");
        byte[] signature = new byte[128];
        Assertions.assertTrue(SetSignAndVerify.SignVerifyECDSA(dataToBeSigned, signature));
        short sigLen = SetSignAndVerify.SignECDSA(dataToBeSigned, signature);
        Assertions.assertTrue(SetSignAndVerify.verifyECDSA(dataToBeSigned, (short) dataToBeSigned.length, signature, sigLen));
    }

    @Test
    void appletTwoExactOutputsWithSSTest() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction1);
        byte[] transaction2 = fromHex(TestTransactions.validTransaction2);

        byte[] value1 = fromHex("603bea0b00000000");
        byte[] scriptSig1 = fromHex("76a914768a40bbd740cbe81d988e71de2a4d5c71396b1d88ac");

        byte[] value2 = fromHex("8e24000000000000");
        byte[] scriptSig2 = fromHex("76a9146f4620b553fa095e721b9ee0efe9fa039cca459788ac");

        byte[] policy = fromHex("0200010d0300010d0202030d030203"); // policy checks exact output value with ScriptSig

        upload.sendData(value1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(scriptSig1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);
        upload.sendData(value2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 2, (byte) 0, manager);
        upload.sendData(scriptSig2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 3, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));

        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager) );
    }

    @Test
    void appletMaxOwSSTest3() throws Exception { //OtTA stands for Output with Script Signature
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction2 = fromHex(TestTransactions.validTransaction2);
        byte[] value1 = fromHex("0000000000000000");
        byte[] scriptSig1 = fromHex("76a914768a40bbd740cbe81d988e71de2a4d5c71396b1d88ac");

        byte[] policy = fromHex("030001"); // policy checks min output value with ScriptSig, addition: + "0d" + "030001"

        upload.sendData(value1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(scriptSig1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));
    }

    @Test
    void appletMaxOwSSTest1() throws Exception { //OtTA stands for Output with Script Signature
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction1);
        byte[] transaction2 = fromHex(TestTransactions.validTransaction2);

        byte[] value1 = fromHex("603bea0b00000000");
        byte[] scriptSig1 = fromHex("76a914768a40bbd740cbe81d988e71de2a4d5c71396b1d88ac");

        byte[] policy = fromHex("030001"); // policy checks min output value with ScriptSig, addition: + "0d" + "030001"

        upload.sendData(value1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(scriptSig1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));

        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));

        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));
    }

    @Test
    void appletMaxOwSSTest2() throws Exception { //OtTA stands for Output with Script Signature
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction2 = fromHex(TestTransactions.validTransaction2);
        byte[] value1 = fromHex("ffffffffffffffff");
        byte[] scriptSig1 = fromHex("76a914768a40bbd740cbe81d988e71de2a4d5c71396b1d88ac");

        byte[] policy = fromHex("030001"); // policy checks min output value with ScriptSig, addition: + "0d" + "030001"

        upload.sendData(value1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(scriptSig1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));
    }

    @Test
    void appletMinOwSSTest1() throws Exception { //OtTA stands for Output with Script Signature
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction1);
        byte[] transaction2 = fromHex(TestTransactions.validTransaction2);

        byte[] value1 = fromHex("603bea0b00000000");
        byte[] scriptSig1 = fromHex("76a914768a40bbd740cbe81d988e71de2a4d5c71396b1d88ac");

        byte[] policy = fromHex("020001"); // policy checks min output value with ScriptSig, addition: + "0d" + "030001"

        upload.sendData(value1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(scriptSig1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));

        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));

        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));
    }

    @Test
    void appletMinOwSSTest2() throws Exception { //OtTA stands for Output with Script Signature
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction1);
        byte[] transaction2 = fromHex(TestTransactions.validTransaction2);

        byte[] value2 = fromHex("8e24000000000000");
        byte[] scriptSig2 = fromHex("76a9146f4620b553fa095e721b9ee0efe9fa039cca459788ac");

        // those values are taken directly from sparrow from transaction2

        byte[] policy = fromHex("020001"); // policy checks min output value with ScriptSig, addition: + "0d" + "030001"

        upload.sendData(value2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(scriptSig2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));

        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager) );

        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));
    }

    @Test
    void appletMinOwSSTest3() throws Exception { //OtTA stands for Output with Script Signature
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction1);
        byte[] transaction2 = fromHex(TestTransactions.validTransaction2);

        byte[] minvalue = fromHex("0000000000000000");
        byte[] scriptSig1 = fromHex("76a914768a40bbd740cbe81d988e71de2a4d5c71396b1d88ac");

        byte[] policy = fromHex("020001"); // policy checks min output value with ScriptSig, addition: + "0d" + "030001"

        upload.sendData(minvalue, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(scriptSig1, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));

        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));

        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));
    }

    @Test
    void simpleAppletTest() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction1);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);

        byte[] policy = fromHex("0a000d04010d05010d0a00"); // policy checks one secret and exactly one input and psbt version 0
        byte[] secret = fromHex("01020304050102030405");
        byte additionalDataSequence = 0;
        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADD_DATA_UP, additionalDataSequence, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);

        Assertions.assertEquals(0, download.downloadValidation(manager));

        byte checkAgainstDataSequence = 0;
        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADD_DATA_UP, checkAgainstDataSequence, (byte) 0, manager);

        Assertions.assertEquals(1, download.downloadValidation(manager) );
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
    }

    @Test
    void appletLiveThroughWithTransaction1() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction1);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 1;
        assert download.downloadNumOfOut(manager) == 2;
        byte[] policy = fromHex("0a000d04010d05010d0a00"); // policy checks one secret and exactly one input and psbt version 0
        byte[] secret = fromHex("01020304050102030405");
        byte additionalDataSequence = 0;
        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADD_DATA_UP, additionalDataSequence, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);

        Assertions.assertEquals(0, download.downloadValidation(manager));

        byte checkAgainstDataSequence = 0;
        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADD_DATA_UP, checkAgainstDataSequence, (byte) 0, manager);

        Assertions.assertEquals(1, download.downloadValidation(manager) );
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
    }


    @Test
    void applet2OutOf3SecretTest() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] policy = fromHex("0a00"+"0a01"+"0d"+"0a01"+"0a02"+"0d"+"0a00"+"0a02"); // policy for 2-3 secrets

        byte[] secret = fromHex("01020304050102030405");
        byte[] secret2 = fromHex("11111111111111111111");
        byte[] secret3 = fromHex("33333333333102030405");
        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(secret2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);
        upload.sendData(secret3, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 2, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        Assertions.assertEquals(0, download.downloadValidation(manager));

        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));

        upload.sendData(secret2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));

        upload.sendData(secret3, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 2, (byte) 0, manager);
        Assertions.assertEquals(0, download.downloadValidation(manager));

        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(secret2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));

        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 0, (byte) 0, manager);
        upload.sendData(secret3, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 2, (byte) 0, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));

        upload.sendData(secret2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 1, (byte) 0, manager);
        upload.sendData(secret3, (byte) AppletInstructions.CLASS_ADD_DATA_UP, (byte) 2, (byte) 0, manager);
        Assertions.assertEquals(1, download.downloadValidation(manager));
    }

    @Test
    void appletSecretTest() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] policy = fromHex("0a000d0a010d0a02"); // policy checks three secret

        byte[] secret = fromHex("01020304050102030405");
        byte[] secret2 = fromHex("11111111111111111111");
        byte[] secret3 = fromHex("33333333333102030405");
        byte additionalDataSequence = 0;

        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADD_DATA_UP, additionalDataSequence, (byte) 0, manager);
        additionalDataSequence++;
        upload.sendData(secret2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, additionalDataSequence, (byte) 0, manager);
        additionalDataSequence++;
        upload.sendData(secret3, (byte) AppletInstructions.CLASS_ADD_DATA_UP, additionalDataSequence, (byte) 0, manager);

        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UP, manager);

        Assertions.assertEquals(0, download.downloadValidation(manager));

        byte checkAgainstDataSequence = 0;
        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADD_DATA_UP, checkAgainstDataSequence, (byte) 0, manager);
        checkAgainstDataSequence++;
        upload.sendData(secret2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, checkAgainstDataSequence, (byte) 0, manager);
        //checkAgainstDataSequence++; // secret num3 writes itself on shelf of secret2
        upload.sendData(secret3, (byte) AppletInstructions.CLASS_ADD_DATA_UP, checkAgainstDataSequence, (byte) 0, manager);

        Assertions.assertEquals(0, download.downloadValidation(manager));

        checkAgainstDataSequence = 0;
        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADD_DATA_UP, checkAgainstDataSequence, (byte) 0, manager);
        checkAgainstDataSequence++;
        upload.sendData(secret2, (byte) AppletInstructions.CLASS_ADD_DATA_UP, checkAgainstDataSequence, (byte) 0, manager);
        checkAgainstDataSequence++;
        upload.sendData(secret3, (byte) AppletInstructions.CLASS_ADD_DATA_UP, checkAgainstDataSequence, (byte) 0, manager);

        Assertions.assertEquals(1, download.downloadValidation(manager));
    }


    @Test
    public void transaction01() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction1);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 1;
        assert download.downloadNumOfOut(manager) == 2;
        download.downloadSize(manager);
        Assertions.assertEquals(bytesToHex(download.downloadInput(manager, (byte) 0)), "268171371edff285e937adeea4b37b78000c0566cbb3ad64641713ca42171bf60000000000feffffff");
        Assertions.assertEquals(bytesToHex(download.downloadOutput(manager, (byte) 0)), "d3dff505000000001976a914d0c59903c5bac2868760e90fd521a4665aa7652088ac");
        Assertions.assertEquals(bytesToHex(download.downloadOutput(manager, (byte) 1)), "00e1f5050000000017a9143545e6e33b832c47050f24d3eeb93c9c03948bc787");
        System.out.print("Test transaction1: passed" + System.lineSeparator());
    }

    @Test
    public void transaction02() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction2);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 2;
        assert download.downloadNumOfOut(manager) == 2;
        download.downloadSize(manager);
        assertEquals(bytesToHex(download.downloadInput(manager, (byte) 0)), "ab0949a08c5af7c49b8212f417e2f15ab3f5c33dcf153821a8139f877a5b7be40000000000feffffff");
        assertEquals(bytesToHex(download.downloadInput(manager, (byte) 1)), "ab0949a08c5af7c49b8212f417e2f15ab3f5c33dcf153821a8139f877a5b7be40100000000feffffff");
        assertEquals(bytesToHex(download.downloadOutput(manager, (byte) 0)), "603bea0b000000001976a914768a40bbd740cbe81d988e71de2a4d5c71396b1d88ac");
        assertEquals(bytesToHex(download.downloadOutput(manager, (byte) 1)), "8e240000000000001976a9146f4620b553fa095e721b9ee0efe9fa039cca459788ac");
        System.out.print("Test transaction2: passed" + System.lineSeparator());
    }

    @Test
    public void transaction03() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction3);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 1;
        assert download.downloadNumOfOut(manager) == 2;
        download.downloadSize(manager);
        assertEquals(bytesToHex(download.downloadInput(manager, (byte) 0)), "268171371edff285e937adeea4b37b78000c0566cbb3ad64641713ca42171bf60000000000feffffff");
        assertEquals(bytesToHex(download.downloadOutput(manager, (byte) 0)), "d3dff505000000001976a914d0c59903c5bac2868760e90fd521a4665aa7652088ac");
        assertEquals(bytesToHex(download.downloadOutput(manager, (byte) 1)), "00e1f5050000000017a9143545e6e33b832c47050f24d3eeb93c9c03948bc787");
        System.out.print("Test transaction3: passed" + System.lineSeparator());
    }

    @Test
    public void transaction04() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction4);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 2;
        assert download.downloadNumOfOut(manager) == 2;
        assertEquals(bytesToHex(download.downloadInput(manager, (byte) 0)), "ab0949a08c5af7c49b8212f417e2f15ab3f5c33dcf153821a8139f877a5b7be40000000000feffffff");
        assertEquals(bytesToHex(download.downloadInput(manager, (byte) 1)), "ab0949a08c5af7c49b8212f417e2f15ab3f5c33dcf153821a8139f877a5b7be40100000000feffffff");
        assertEquals(bytesToHex(download.downloadOutput(manager, (byte) 0)), "603bea0b000000001976a914768a40bbd740cbe81d988e71de2a4d5c71396b1d88ac");
        assertEquals(bytesToHex(download.downloadOutput(manager, (byte) 1)), "8e240000000000001976a9146f4620b553fa095e721b9ee0efe9fa039cca459788ac");
        System.out.print("Test transaction4: passed" + System.lineSeparator());
    }

    @Test
    public void transaction05() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction5);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 1;
        assert download.downloadNumOfOut(manager) == 1;
        assertEquals(bytesToHex(download.downloadInput(manager, (byte) 0)), "279a2323a5dfb51fc45f220fa58b0fc13e1e3342792a85d7e36cd6333b5cbc390000000000ffffffff");
        assertEquals(bytesToHex(download.downloadOutput(manager, (byte) 0)), "a05aea0b000000001976a914ffe9c0061097cc3b636f2cb0460fa4fc427d2b4588ac");
        download.downloadSize(manager);
        System.out.print("Test transaction5: passed" + System.lineSeparator());
    }

    @Test
    public void transaction06() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction6);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 1;
        assert download.downloadNumOfOut(manager) == 1;
        //assertEquals(bytesToHex(download.downloadInputV0(manager, (byte) 0)), ""); //sparrow does not open this transaction
        //assertEquals(bytesToHex(download.downloadOutputV0(manager, (byte) 0)), ""); //sparrow does not open this transaction
        download.downloadSize(manager);
        download.downloadInput(manager, (byte) 0);
        download.downloadOutput(manager, (byte) 0);
        System.out.print("Test transaction6: passed" + System.lineSeparator());
    }

    @Test
    public void transaction07() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction7);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 1;
        assert download.downloadNumOfOut(manager) == 1;
        assertEquals(bytesToHex(download.downloadInput(manager, (byte) 0)), "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0000000000ffffffff");
        assertEquals(bytesToHex(download.downloadOutput(manager, (byte) 0)), "0000000000000000036a0100");
        download.downloadSize(manager);
        System.out.print("Test transaction7: passed" + System.lineSeparator());
    }

    @Test
    public void transaction08() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction8);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 2;
        assert download.downloadNumOfOut(manager) == 2;
        assertEquals(bytesToHex(download.downloadInput(manager, (byte) 0)), "710ea76ab45c5cb6438e607e59cc037626981805ae9e0dfd9089012abb0be5350100000000ffffffff");
        assertEquals(bytesToHex(download.downloadInput(manager, (byte) 1)), "190994d6a8b3c8c82ccbcfb2fba4106aa06639b872a8d447465c0d42588d6d670000000000ffffffff");
        assertEquals(bytesToHex(download.downloadOutput(manager, (byte) 0)), "00e1f505000000001976a914b6bc2c0ee5655a843d79afedd0ccc3f7dd64340988ac");
        assertEquals(bytesToHex(download.downloadOutput(manager, (byte) 1)), "605af405000000001600141188ef8e4ce0449eaac8fb141cbf5a1176e6a088");
        download.downloadSize(manager);
        System.out.print("Test transaction8: passed" + System.lineSeparator());
    }

    @Test
    public void transaction09() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction9);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 0;
        assert download.downloadNumOfOut(manager) == 0;
        download.downloadSize(manager);
        System.out.print("Test transaction9: passed" + System.lineSeparator());
    }

    @Test
    public void transaction10() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction10);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 0;
        assert download.downloadNumOfOut(manager) == 2;
        // sparrow refuses to open this transaction
        System.out.print("Test transaction10: passed" + System.lineSeparator());
    }

    @Test
    public void transaction11() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction11);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 1;
        assert download.downloadNumOfOut(manager) == 2;
        // sparrow refuses to open this transaction
        System.out.print("Test transaction11: passed" + System.lineSeparator());
    }

    @Test
    public void transaction12() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction12);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 1;
        assert download.downloadNumOfOut(manager) == 2;
        // sparrow refuses to open this transaction
    }

    @Test
    public void transaction13() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction13);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 1;
        assert download.downloadNumOfOut(manager) == 2;

        // sparrow refuses to open this transaction
    }

    @Test
    public void transaction14() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction14);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 1;
        assert download.downloadNumOfOut(manager) == 2;
        // sparrow refuses to open this transaction
    }

    @Test
    public void transaction15() throws Exception {
        Download download = new Download();
        Upload upload = new Upload();
        CardManager manager = connect();
        byte[] transaction = fromHex(TestTransactions.validTransaction15);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UP, manager);
        assert download.downloadNumOfInp(manager) == 1;
        assert download.downloadNumOfOut(manager) == 2;
        // sparrow refuses to open this transaction
    }
}
