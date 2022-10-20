package tests;
import applet.*;
import cz.muni.fi.crocs.rcard.client.CardManager;

import java.util.Arrays;

public class AppletControl extends BaseTest {
    CardManager manager = connect();
    Upload mu = new Upload();
    Download md = new Download();
    byte[] psbt = null;

    public void UploadData(CardManager manager, Upload mu) throws Exception {
        mu.sendData(psbt, (byte) AppletInstructions.CLASS_PSBT_UPLOAD, manager);
    }

    public void DownloadDebugV0(CardManager manager) throws Exception {
        System.out.print("Parsing transaction of version: " + md.downloadVersion(manager) + (" (-1 means unspecified)") + System.lineSeparator());
        System.out.print("And of size: " + md.downloadSize(manager) + " bytes" + System.lineSeparator());
        short inps = md.downloadNumOfInp(manager);
        short outs = md.downloadNumOfOut(manager);
        System.out.print("Number of inputs: " + inps + System.lineSeparator());
        System.out.print("Number of outputs: " + outs + System.lineSeparator());

        System.out.print(Arrays.toString(md.downloadMap(manager, AppletInstructions.CLASS_DOWNLOAD_GLOBAL_ALL, (byte) 0)) + System.lineSeparator());

        int i;

        for (i = 0; i < inps; i++){
            System.out.print("Input with index: " + i + System.lineSeparator());
            System.out.print(Arrays.toString(md.downloadInputV0(manager, (byte) i)) + System.lineSeparator());
        }
        for (i = 0; i < outs; i++){
            System.out.print("Output with index: " + i + System.lineSeparator());
            System.out.print(Arrays.toString(md.downloadOutputV0(manager, (byte) i)) + System.lineSeparator());
        }
    }


    public void AppletDebugV0() throws Exception {
        UploadData(manager, mu);
        DownloadDebugV0(manager);
        //manager.disconnect(true); // this does not help
    }

    public AppletControl(String psbt) throws Exception {
        this.psbt = MyTests.fromHex(psbt);
    }

    public AppletControl(byte[] psbt) throws Exception {
        this.psbt = psbt;
    }

    public AppletControl() throws Exception {
    }
}




