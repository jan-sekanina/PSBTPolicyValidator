package tests;

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
