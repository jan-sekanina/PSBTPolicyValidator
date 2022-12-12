package tests;

import com.licel.jcardsim.smartcardio.CardSimulator;
import cz.muni.fi.crocs.rcard.client.CardManager;
import main.Download;
import main.AppletControl;

import cz.muni.fi.crocs.rcard.client.CardType;
import org.junit.jupiter.api.*;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Example test class for the applet
 * Note: If simulator cannot be started try adding "-noverify" JVM parameter
 *
 * @author xsvenda, Dusan Klinec (ph4r05)
 */
public class AppletTest extends BaseTest {
    public AppletTest() {
        // Change card type here if you want to use physical card
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
    public void hell() throws Exception {
        String welcome = "I am so fucking happy I finally understand how this works!" + System.lineSeparator();
        byte[] welcomeB = welcome.getBytes(StandardCharsets.UTF_8);
        final CommandAPDU cmd = new CommandAPDU(0x00, 0x80, 0, 0, welcomeB, 0, welcomeB.length, 32);
        final ResponseAPDU responseAPDU = connect().transmit(cmd);

        Assertions.assertNotNull(responseAPDU);
        assertEquals(0x9000, responseAPDU.getSW());
        Assertions.assertNotNull(responseAPDU.getBytes());
        System.out.print(Arrays.toString(responseAPDU.getBytes()) + System.lineSeparator());
        System.out.print("Test hell: passed" + System.lineSeparator());
    }

    //@Test
    public void hel() throws Exception {
        CardManager man = connect();
        Download dwn = new Download();
        //AppletControl appletControl = new AppletControl(man, TransactionsImported.validTransaction1);
        String welcome = "I am so fucking happy I finally understand how this works!" + System.lineSeparator();
        byte[] welcomeB = welcome.getBytes(StandardCharsets.UTF_8);
        final CommandAPDU cmd = new CommandAPDU(0x00, 0x80, 0, 0, welcomeB, 0, welcomeB.length, 32);
        final ResponseAPDU responseAPDU = connect().transmit(cmd);

        Assertions.assertNotNull(responseAPDU);
        assertEquals(0x9000, responseAPDU.getSW());
        Assertions.assertNotNull(responseAPDU.getBytes());
        System.out.print(Arrays.toString(responseAPDU.getBytes()) + System.lineSeparator());
        System.out.print("Test hell: passed" + System.lineSeparator());
    }
    @Test
    public void transaction1() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction1);
        Download download = new Download();
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 1;
        assert download.downloadNumOfOut(simulator) == 2;
        download.downloadSize(simulator);
        assertEquals(ac.bytesToHex(download.downloadInput(simulator, (byte) 0)), "268171371edff285e937adeea4b37b78000c0566cbb3ad64641713ca42171bf60000000000feffffff");
        assertEquals(ac.bytesToHex(download.downloadOutput(simulator, (byte) 0)), "d3dff505000000001976a914d0c59903c5bac2868760e90fd521a4665aa7652088ac");
        assertEquals(ac.bytesToHex(download.downloadOutput(simulator, (byte) 1)), "00e1f5050000000017a9143545e6e33b832c47050f24d3eeb93c9c03948bc787");
        System.out.print("Test transaction1: passed" + System.lineSeparator());
    }

    @Test
    public void transaction2() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction2);
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 2;
        assert download.downloadNumOfOut(simulator) == 2;
        download.downloadSize(simulator);
        assertEquals(ac.bytesToHex(download.downloadInput(simulator, (byte) 0)), "ab0949a08c5af7c49b8212f417e2f15ab3f5c33dcf153821a8139f877a5b7be40000000000feffffff");
        assertEquals(ac.bytesToHex(download.downloadInput(simulator, (byte) 1)), "ab0949a08c5af7c49b8212f417e2f15ab3f5c33dcf153821a8139f877a5b7be40100000000feffffff");
        assertEquals(ac.bytesToHex(download.downloadOutput(simulator, (byte) 0)), "603bea0b000000001976a914768a40bbd740cbe81d988e71de2a4d5c71396b1d88ac");
        assertEquals(ac.bytesToHex(download.downloadOutput(simulator, (byte) 1)), "8e240000000000001976a9146f4620b553fa095e721b9ee0efe9fa039cca459788ac");
        System.out.print("Test transaction2: passed" + System.lineSeparator());
    }

    @Test
    public void transaction3() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction3);
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 1;
        assert download.downloadNumOfOut(simulator) == 2;
        download.downloadSize(simulator);
        assertEquals(ac.bytesToHex(download.downloadInput(simulator, (byte) 0)), "268171371edff285e937adeea4b37b78000c0566cbb3ad64641713ca42171bf60000000000feffffff");
        assertEquals(ac.bytesToHex(download.downloadOutput(simulator, (byte) 0)), "d3dff505000000001976a914d0c59903c5bac2868760e90fd521a4665aa7652088ac");
        assertEquals(ac.bytesToHex(download.downloadOutput(simulator, (byte) 1)), "00e1f5050000000017a9143545e6e33b832c47050f24d3eeb93c9c03948bc787");
        System.out.print("Test transaction3: passed" + System.lineSeparator());
    }

    @Test
    public void transaction4() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction4);
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 2;
        assert download.downloadNumOfOut(simulator) == 2;
        assertEquals(ac.bytesToHex(download.downloadInput(simulator, (byte) 0)), "ab0949a08c5af7c49b8212f417e2f15ab3f5c33dcf153821a8139f877a5b7be40000000000feffffff");
        assertEquals(ac.bytesToHex(download.downloadInput(simulator, (byte) 1)), "ab0949a08c5af7c49b8212f417e2f15ab3f5c33dcf153821a8139f877a5b7be40100000000feffffff");
        assertEquals(ac.bytesToHex(download.downloadOutput(simulator, (byte) 0)), "603bea0b000000001976a914768a40bbd740cbe81d988e71de2a4d5c71396b1d88ac");
        assertEquals(ac.bytesToHex(download.downloadOutput(simulator, (byte) 1)), "8e240000000000001976a9146f4620b553fa095e721b9ee0efe9fa039cca459788ac");
        System.out.print("Test transaction4: passed" + System.lineSeparator());
    }

    @Test
    public void transaction5() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction5);
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 1;
        assert download.downloadNumOfOut(simulator) == 1;
        assertEquals(ac.bytesToHex(download.downloadInput(simulator, (byte) 0)), "279a2323a5dfb51fc45f220fa58b0fc13e1e3342792a85d7e36cd6333b5cbc390000000000ffffffff");
        assertEquals(ac.bytesToHex(download.downloadOutput(simulator, (byte) 0)), "a05aea0b000000001976a914ffe9c0061097cc3b636f2cb0460fa4fc427d2b4588ac");
        download.downloadSize(simulator);
        System.out.print("Test transaction5: passed" + System.lineSeparator());
    }

    @Test
    public void transaction6() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction6);
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 1;
        assert download.downloadNumOfOut(simulator) == 1;
        //assertEquals(ac.bytesToHex(download.downloadInputV0(simulator, (byte) 0)), ""); //sparrow does not open this transaction
        //assertEquals(ac.bytesToHex(download.downloadOutputV0(simulator, (byte) 0)), ""); //sparrow does not open this transaction
        download.downloadSize(simulator);
        download.downloadInput(simulator, (byte) 0);
        download.downloadOutput(simulator, (byte) 0);
        System.out.print("Test transaction6: passed" + System.lineSeparator());
    }

    @Test
    public void transaction7() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction7);
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 1;
        assert download.downloadNumOfOut(simulator) == 1;
        assertEquals(ac.bytesToHex(download.downloadInput(simulator, (byte) 0)), "ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff0000000000ffffffff");
        assertEquals(ac.bytesToHex(download.downloadOutput(simulator, (byte) 0)), "0000000000000000036a0100");
        download.downloadSize(simulator);
        System.out.print("Test transaction7: passed" + System.lineSeparator());
    }

    @Test
    public void transaction8() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction8);
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 2;
        assert download.downloadNumOfOut(simulator) == 2;
        assertEquals(ac.bytesToHex(download.downloadInput(simulator, (byte) 0)), "710ea76ab45c5cb6438e607e59cc037626981805ae9e0dfd9089012abb0be5350100000000ffffffff");
        assertEquals(ac.bytesToHex(download.downloadInput(simulator, (byte) 1)), "190994d6a8b3c8c82ccbcfb2fba4106aa06639b872a8d447465c0d42588d6d670000000000ffffffff");
        assertEquals(ac.bytesToHex(download.downloadOutput(simulator, (byte) 0)), "00e1f505000000001976a914b6bc2c0ee5655a843d79afedd0ccc3f7dd64340988ac");
        assertEquals(ac.bytesToHex(download.downloadOutput(simulator, (byte) 1)), "605af405000000001600141188ef8e4ce0449eaac8fb141cbf5a1176e6a088");
        download.downloadSize(simulator);
        System.out.print("Test transaction8: passed" + System.lineSeparator());
    }

    @Test
    public void transaction9() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction9);
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 0;
        assert download.downloadNumOfOut(simulator) == 0;
        download.downloadSize(simulator);
        System.out.print("Test transaction9: passed" + System.lineSeparator());
    }

    @Test
    public void transaction10() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction10);
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 0;
        assert download.downloadNumOfOut(simulator) == 2;
        // assertEquals(ac.bytesToHex(download.downloadOutputV0(simulator, (byte) 0)), ""); // sparrow refuses to open this transaction
        // assertEquals(ac.bytesToHex(download.downloadOutputV0(simulator, (byte) 1)), ""); // sparrow refuses to open this transaction
        System.out.print("Test transaction10: passed" + System.lineSeparator());
    }

    /**
    @Test
    public void transaction11() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction11);
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 1;
        assert download.downloadNumOfOut(simulator) == 2;
        // assertEquals(ac.bytesToHex(download.downloadInputV0(simulator, (byte) 0)), ""); // sparrow refuses to open this transaction
        // assertEquals(ac.bytesToHex(download.downloadOutputV0(simulator, (byte) 0)), ""); // sparrow refuses to open this transaction
        // assertEquals(ac.bytesToHex(download.downloadOutputV0(simulator, (byte) 1)), ""); // sparrow refuses to open this transaction
        System.out.print("Test transaction11: passed" + System.lineSeparator());
    }

    @Test
    public void transaction12() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction12);
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 1;
        assert download.downloadNumOfOut(simulator) == 2;

        // assertEquals(ac.bytesToHex(download.downloadInputV0(simulator, (byte) 0)), ""); // sparrow refuses to open this transaction
        // assertEquals(ac.bytesToHex(download.downloadOutputV0(simulator, (byte) 0)), ""); // sparrow refuses to open this transaction
        // assertEquals(ac.bytesToHex(download.downloadOutputV0(simulator, (byte) 1)), ""); // sparrow refuses to open this transaction
    }

    @Test
    public void transaction13() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction13);
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 1;
        assert download.downloadNumOfOut(simulator) == 2;

        // assertEquals(ac.bytesToHex(download.downloadInputV0(simulator, (byte) 0)), ""); // sparrow refuses to open this transaction
        // assertEquals(ac.bytesToHex(download.downloadOutputV0(simulator, (byte) 0)), ""); // sparrow refuses to open this transaction
        // assertEquals(ac.bytesToHex(download.downloadOutputV0(simulator, (byte) 1)), ""); // sparrow refuses to open this transaction
    }

    @Test
    public void transaction14() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction14);
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 1;
        assert download.downloadNumOfOut(simulator) == 2;
        // assertEquals(ac.bytesToHex(download.downloadInputV0(simulator, (byte) 0)), ""); // sparrow refuses to open this transaction
        // assertEquals(ac.bytesToHex(download.downloadOutputV0(simulator, (byte) 0)), ""); // sparrow refuses to open this transaction
        // assertEquals(ac.bytesToHex(download.downloadOutputV0(simulator, (byte) 1)), ""); // sparrow refuses to open this transaction
    }

    @Test
    public void transaction15() throws Exception {
        byte[] transaction = AppletControl.fromHex(TransactionsImported.validTransaction15);
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == 1;
        assert download.downloadNumOfOut(simulator) == 2;
        // assertEquals(ac.bytesToHex(download.downloadInputV0(simulator, (byte) 0)), ""); // sparrow refuses to open this transaction
        // assertEquals(ac.bytesToHex(download.downloadOutputV0(simulator, (byte) 0)), ""); // sparrow refuses to open this transaction
        // assertEquals(ac.bytesToHex(download.downloadOutputV0(simulator, (byte) 1)), ""); // sparrow refuses to open this transaction
    }
    **/

    //@Test
    public void runAllTests() throws Exception {
        transactionTestTemplate((byte) 1, (byte) 2, AppletControl.fromHex(TransactionsImported.validTransaction1));
        // Case: PSBT with one P2PKH input. Outputs are empty

        transactionTestTemplate((byte) 2, (byte) 2, AppletControl.fromHex(TransactionsImported.validTransaction2));
        // Case: PSBT with one P2PKH input and one P2SH-P2WPKH input. First input is signed and finalized. Outputs are empty

        transactionTestTemplate((byte) 1, (byte) 2, AppletControl.fromHex(TransactionsImported.validTransaction3));
        // Case: PSBT with one P2PKH input which has a non-final scriptSig and has a sighash type specified. Outputs are empty

        transactionTestTemplate((byte) 2, (byte) 2, AppletControl.fromHex(TransactionsImported.validTransaction4));
        // Case: PSBT with one P2PKH input and one P2SH-P2WPKH input both with non-final scriptSigs. P2SH-P2WPKH input's redeemScript is available. Outputs filled.

        transactionTestTemplate((byte) 1, (byte) 1, AppletControl.fromHex(TransactionsImported.validTransaction5));
        // Case: PSBT with one P2SH-P2WSH input of a 2-of-2 multisig, redeemScript, witnessScript, and keypaths are available. Contains one signature.

        transactionTestTemplate((byte) 1, (byte) 1, AppletControl.fromHex(TransactionsImported.validTransaction6));
        // Case: PSBT with one P2WSH input of a 2-of-2 multisig. witnessScript, keypaths, and global xpubs are available. Contains no signatures. Outputs filled.

        transactionTestTemplate((byte) 1, (byte) 1, AppletControl.fromHex(TransactionsImported.validTransaction7));
        // Case: PSBT with unknown types in the inputs.

        transactionTestTemplate((byte) 2, (byte) 2, AppletControl.fromHex(TransactionsImported.validTransaction8));
        // Case: PSBT with `PSBT_GLOBAL_XPUB`

        transactionTestTemplate((byte) 0, (byte) 0, AppletControl.fromHex(TransactionsImported.validTransaction9));
        // Case: PSBT with global unsigned tx that has 0 inputs and 0 outputs

        transactionTestTemplate((byte) 0, (byte) 2, AppletControl.fromHex(TransactionsImported.validTransaction10));
        // Case: PSBT with 0 inputs
    }


    static void transactionTestTemplate(byte expectedInput, byte expectedOutput, byte[] transaction) throws Exception {
        CardSimulator simulator = new CardSimulator();
        AppletControl ac = new AppletControl(simulator, transaction);
        Download download = new Download();
        ac.UploadTransaction();
        assert download.downloadNumOfInp(simulator) == expectedInput;
        assert download.downloadNumOfOut(simulator) == expectedOutput;
    }
    //@Test
    public void callMyTest() throws Exception {
        //AppletControl AC1stTransaction = new AppletControl(TransactionsImported.validTransaction1);
        //AppletControl AC2stTransaction = new AppletControl(TransactionsImported.validTransaction2);
        //AppletControl AC3stTransaction = new AppletControl(TransactionsImported.validTransaction3);
        //AppletControl AC4stTransaction = new AppletControl(TransactionsImported.validTransaction4);
        //AppletControl AC5stTransaction = new AppletControl(TransactionsImported.validTransaction5);
        //AppletControl AC6stTransaction = new AppletControl(TransactionsImported.validTransaction6);
        //AppletControl AC7stTransaction = new AppletControl(TransactionsImported.validTransaction7);
        //AppletControl AC8stTransaction = new AppletControl(TransactionsImported.validTransaction8);
        //AppletControl AC9stTransaction = new AppletControl(TransactionsImported.validTransaction9);
        AppletControl AC10stTransaction = new AppletControl(TransactionsImported.validTransaction10);

        //AC1stTransaction.AppletDebugV0();
        //AC2stTransaction.AppletDebugV0();
        /**
        AC3stTransaction.AppletDebugV0();
        AC4stTransaction.AppletDebugV0();
        AC5stTransaction.AppletDebugV0();
        AC6stTransaction.AppletDebugV0();
        AC7stTransaction.AppletDebugV0();
        AC8stTransaction.AppletDebugV0();
         */
        //AC9stTransaction.AppletDebugV0();
        AC10stTransaction.AppletDebug();

    }
}
