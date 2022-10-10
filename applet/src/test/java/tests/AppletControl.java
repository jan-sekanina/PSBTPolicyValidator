package tests;
import applet.*;
import cz.muni.fi.crocs.rcard.client.CardManager;

import java.util.Arrays;

public class AppletControl extends BaseTest {
    CardManager manager = connect();
    Upload mu = new Upload();
    Download md = new Download();
    byte[] psbt = MyTests.fromHex(TransactionsImported.validTransaction1);

    public void UploadData(CardManager manager, Upload mu) throws Exception {
        mu.sendData(psbt, (byte) AppletInstructions.CLASS_PSBT_UPLOAD, manager);
    }

    public void DownloadDebug(CardManager manager) throws Exception {
        System.out.print("Parsing transaction of version: " + md.downloadVersion(manager) + (" (-1 means unspecified)") + System.lineSeparator());
        System.out.print("And of size: " + md.downloadSize(manager) + " bytes" + System.lineSeparator());
        short inps = md.downloadNumOfInp(manager);
        short outs = md.downloadNumOfOut(manager);
        System.out.print("Number of inputs: " + inps + System.lineSeparator());
        System.out.print("Number of outputs: " + outs + System.lineSeparator());
        System.out.print(Arrays.toString(md.downloadDebugArray(manager, (short) 0, (short) 100)) + System.lineSeparator());

        System.out.print(Arrays.toString(md.downloadMap(manager, AppletInstructions.CLASS_DOWNLOAD_GLOBAL_ALL, (byte) 0)) + System.lineSeparator());
        int i;
        for (i = 0; i <= 0; i++){ // it is very possible to break if global keypair with index i is absent
            System.out.print("Global map keypair with index: " + i + System.lineSeparator());
            System.out.print(Arrays.toString(md.downloadGlobalKeypair(manager, i)) + System.lineSeparator());
        }
        for (i = 0; i <= 3; i++){
            System.out.print("Output map array with index: " + i + System.lineSeparator());
            System.out.print(Arrays.toString(md.downloadMap(manager, AppletInstructions.CLASS_DOWNLOAD_OUTPUT_ALL, (byte) 0)) + System.lineSeparator());
            System.out.print("Input map array with index: " + i + System.lineSeparator());
            System.out.print(Arrays.toString(md.downloadMap(manager, AppletInstructions.CLASS_DOWNLOAD_INPUT_ALL, (byte) 0)) + System.lineSeparator());
        }
        //System.out.print(Arrays.toString(du.downloadDebugArray(manager, (short) 0, (short) 555)) + System.lineSeparator());
    }


    public void AppletDebug() throws Exception {
        UploadData(manager, mu);
        DownloadDebug(manager);
    }


    public AppletControl() throws Exception {
    }
}




