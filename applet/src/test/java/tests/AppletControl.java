package tests;
import applet.*;
import cz.muni.fi.crocs.rcard.client.CardManager;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class AppletControl extends BaseTest{
    CardManager manager = connect();

    public void DownloadTest() throws Exception {
        Upload mu = new Upload();
        Download du = new Download();
        byte[] psbt = MyTests.fromHex(TransactionsImported.validTransaction1);
        mu.sendData(psbt, (byte) AppletInstructions.CLASS_PSBT_UPLOAD, manager);
        System.out.print(Arrays.toString(du.downloadMap(manager, AppletInstructions.CLASSS_DOWNLOAD_GLOBAL_ALL, (byte) 0)) + System.lineSeparator());
        System.out.print(Arrays.toString(du.downloadMap(manager, AppletInstructions.CLASSS_DOWNLOAD_OUTPUT_ALL, (byte) 0)) + System.lineSeparator());
        System.out.print(Arrays.toString(du.downloadMap(manager, AppletInstructions.CLASSS_DOWNLOAD_INPUT_ALL, (byte) 0)) + System.lineSeparator());
        System.out.print(Arrays.toString(du.downloadDebugArray(manager, (short) 120, (short) 3000)) + System.lineSeparator());
        //System.out.print(Arrays.toString(du.downloadDebugArray(manager, (short) 0, (short) 555)) + System.lineSeparator());
    }


    public AppletControl() throws Exception {
    }


}
