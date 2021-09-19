package tests;

import org.junit.Assert;
import applet.AppletInstructions;

import javax.smartcardio.CommandAPDU;
import javax.smartcardio.ResponseAPDU;


/**
 * class of my tests
 */
public class MyTests extends BaseTest {
    final AppletInstructions instructions = new AppletInstructions();
    final short CLASS_UPLOAD = instructions.getCLASS_UPLOAD() ;
    final short UPLOAD = instructions.getUPLOAD();
    final short UPLOAD_REQUEST = instructions.getUPLOAD_REQUEST();
    final short CLASS_PRINT = instructions.getCLASS_PRINT();
    final short PRINT = instructions.getPRINT();
    public final String data = "I would like to let you know that there are some of the most extreme";
    public MyTests() {

    }

    public void testToMakeMeRememberHowThisWorks() throws Exception {
        //helloAlice();
        //fourIntTest((byte) 0x1, (byte) 0x1, (short) 0x0, (short) 0x0);
        //fourIntTest(0x3,0x8,0x0,0x0);
        uploadTest();
    }

    public void sendData(byte[] data) throws Exception {
        ResponseAPDU rsp;
        CommandAPDU cmd;
        int offset;
        int packet_size;
        cmd = new CommandAPDU(CLASS_UPLOAD, UPLOAD_REQUEST, 0, 0);
        rsp = connect().transmit(cmd);
        packet_size = rsp.getSW1(); // returns how big packets can be sent
        for (offset = 0; offset * packet_size <= data.length; offset++) {
            cmd = new CommandAPDU(CLASS_UPLOAD, UPLOAD, offset, data.length / packet_size, data, offset, packet_size, 0);
            rsp = connect().transmit(cmd);
            assert rsp.getSW() == 0x9000;
        }
        cmd = new CommandAPDU(CLASS_PRINT, PRINT, 0, 0);
        rsp = connect().transmit(cmd);
        assert rsp.getSW() == 0x9000;
    }


    public void uploadTest() throws Exception {
        System.out.print(("Upload test:" + System.lineSeparator()));
        /*
        final byte[] data = new byte[4];
        data[0] = (byte) 0x5;
        data[1] = (byte) 0x5;
        data[2] = (short) 0x1;
        data[3] = (short) 0x1;
        Util.arrayCopyNonAtomic(upload, (short) 0, data, (short) 0, (short) upload.length);
        */
        final CommandAPDU cmd =  new CommandAPDU(5, 5, 0, 0);
        final ResponseAPDU responseAPDU = connect().transmit(cmd);
        //System.out.print(responseAPDU.getBytes().toString() + System.lineSeparator());
    }


    void helloAlice() throws Exception {
        System.out.print("\"Hello Alice!\" test:" + System.lineSeparator());
        final CommandAPDU cmd = new CommandAPDU(0, 0, 0, 0);
        final ResponseAPDU responseAPDU = connect().transmit(cmd);
        final ResponseAPDU responseAPDU2 = connect().transmit(cmd);
        System.out.print("\"" + responseAPDU.getBytes() + "\"" + System.lineSeparator());
    }


    public void fourIntTest(byte cla, byte ins, short p1, short p2) throws Exception {
        System.out.print("fourIntTests:" + System.lineSeparator());
        System.out.print("claIN: " + cla + System.lineSeparator());
        System.out.print("insIN: " + ins + System.lineSeparator());
        System.out.print("p1IN: " + p1 + System.lineSeparator());
        System.out.print("p2IN: " + p2 + System.lineSeparator());

        final CommandAPDU cmd = new CommandAPDU(cla, ins, p1, p2);
        final ResponseAPDU responseAPDU = connect().transmit(cmd);

        Assert.assertNotNull(responseAPDU);
        System.out.print("response: " + responseAPDU.getSW() + System.lineSeparator());
        System.out.print("[0]: " + responseAPDU.getBytes()[0] + System.lineSeparator());
        System.out.print("[1]: " + responseAPDU.getBytes()[1] + System.lineSeparator());
        System.out.print("[2]: " + responseAPDU.getBytes()[2] + System.lineSeparator());
        System.out.print("[3]: " + responseAPDU.getBytes()[3] + System.lineSeparator());
        Assert.assertEquals(0x9000, responseAPDU.getSW());
        Assert.assertNotNull(responseAPDU.getBytes());
        System.out.print("End of the tests" + System.lineSeparator());
    }
}
