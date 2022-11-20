package tests;

import applet.MainApplet;
import com.licel.jcardsim.smartcardio.CardSimulator;
import com.licel.jcardsim.utils.AIDUtil;
import javacard.framework.AID;
import main.Download;
import main.Upload;
import main.AppletControl;

import cz.muni.fi.crocs.rcard.client.CardType;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
        Assertions.assertEquals(0x9000, responseAPDU.getSW());
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
        Assertions.assertEquals(0x9000, responseAPDU.getSW());
        Assertions.assertNotNull(responseAPDU.getBytes());
        System.out.print(Arrays.toString(responseAPDU.getBytes()) + System.lineSeparator());
        System.out.print("Test hell: passed" + System.lineSeparator());
    }

    @Test
    public void hel() throws Exception {
        CardSimulator sim = new CardSimulator();
        Download dwn = new Download();
        AppletControl appletControl = new AppletControl(sim, TransactionsImported.validTransaction1);
        String welcome = "I am so fucking happy I finally understand how this works!" + System.lineSeparator();
        byte[] welcomeB = welcome.getBytes(StandardCharsets.UTF_8);
        final CommandAPDU cmd = new CommandAPDU(0x00, 0x80, 0, 0, welcomeB, 0, welcomeB.length, 32);
        final ResponseAPDU responseAPDU = connect().transmit(cmd);

        Assertions.assertNotNull(responseAPDU);
        Assertions.assertEquals(0x9000, responseAPDU.getSW());
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
        download.downloadInputV0(simulator, (byte) 0);
        download.downloadOutputV0(simulator, (byte) 0);
        download.downloadOutputV0(simulator, (byte) 1);
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
        download.downloadInputV0(simulator, (byte) 0);
        download.downloadInputV0(simulator, (byte) 1);
        download.downloadOutputV0(simulator, (byte) 0);
        download.downloadOutputV0(simulator, (byte) 1);
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
        download.downloadInputV0(simulator, (byte) 0);
        download.downloadOutputV0(simulator, (byte) 0);
        download.downloadOutputV0(simulator, (byte) 1);
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
        download.downloadSize(simulator);
        download.downloadInputV0(simulator, (byte) 0);
        download.downloadInputV0(simulator, (byte) 1);
        download.downloadOutputV0(simulator, (byte) 0);
        download.downloadOutputV0(simulator, (byte) 1);
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
        download.downloadSize(simulator);
        download.downloadInputV0(simulator, (byte) 0);
        download.downloadOutputV0(simulator, (byte) 0);
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
        download.downloadSize(simulator);
        download.downloadInputV0(simulator, (byte) 0);
        download.downloadOutputV0(simulator, (byte) 0);
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
        download.downloadSize(simulator);
        download.downloadInputV0(simulator, (byte) 0);
        download.downloadOutputV0(simulator, (byte) 0);
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
        download.downloadSize(simulator);
        download.downloadInputV0(simulator, (byte) 0);
        download.downloadInputV0(simulator, (byte) 1);
        download.downloadOutputV0(simulator, (byte) 0);
        download.downloadOutputV0(simulator, (byte) 1);
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
        download.downloadSize(simulator);
        download.downloadOutputV0(simulator, (byte) 0);
        download.downloadOutputV0(simulator, (byte) 1);
    }

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
        AC10stTransaction.AppletDebugV0();

        // POZOR DĚLÁ TO BORDEL KDYŽ TO ČLOVĚK PUSTÍ VŠECHNO NAJEDNOU, Z NĚJAKÉHO DŮVODU TO BERE ŠPATNÉ HODNOTY
        // SAMOSTATNĚ TO ALE FUNGUJE, WTF

    }
}
