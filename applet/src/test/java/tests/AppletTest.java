package tests;

import cz.muni.fi.crocs.rcard.client.CardType;
import org.junit.Assert;
import org.junit.jupiter.api.*;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;
import java.nio.charset.StandardCharsets;

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
        Assert.assertNotNull(responseAPDU);
        Assert.assertEquals(0x9000, responseAPDU.getSW());
        Assert.assertNotNull(responseAPDU.getBytes());
        System.out.print("Test hello: passed" + System.lineSeparator());
    }

    @Test
    public void hell() throws Exception {
        String welcome = "I am so fucking happy I finally understand how this works!" + System.lineSeparator();
        byte[] welcomeB = welcome.getBytes(StandardCharsets.UTF_8);
        final CommandAPDU cmd = new CommandAPDU(0x00, 0x80, 0, 0, welcomeB, 0, welcomeB.length, 32);
        final ResponseAPDU responseAPDU = connect().transmit(cmd);

        Assert.assertNotNull(responseAPDU);
        Assert.assertEquals(0x9000, responseAPDU.getSW());
        Assert.assertNotNull(responseAPDU.getBytes());
        System.out.print(responseAPDU.getBytes() + System.lineSeparator());
        System.out.print("Test hell: passed" + System.lineSeparator());
    }

    @Test
    public void callMyTest() throws Exception {
        MyTests myTest = new MyTests();
        //myTest.keyFillTest();
        //myTest.valueFillTest();
        //myTest.keyPairFillTest();
        //myTest.generalFillMapTest();
        myTest.sendDataTest(myTest.fromHex(TransactionsImported.validTransaction1));
        myTest.sendDataTest(myTest.fromHex(TransactionsImported.validTransaction2));
        myTest.sendDataTest(myTest.fromHex(TransactionsImported.validTransaction3));
        myTest.sendDataTest(myTest.fromHex(TransactionsImported.validTransaction4));
        //myTest.psbtFillTest();
        //myTest.psbtFillSimpleTest();
    }
}
