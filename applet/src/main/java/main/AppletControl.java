package main;

import applet.AppletInstructions;
import applet.MainApplet;
import com.licel.jcardsim.base.CardManager;
import com.licel.jcardsim.base.Simulator;
import com.licel.jcardsim.smartcardio.CardSimulator;
import com.licel.jcardsim.utils.AIDUtil;
import javacard.framework.AID;

import java.util.Arrays;

public class AppletControl {
    CardSimulator simulator = null;
    Upload mu = new Upload();
    Download md = new Download();
    byte[] psbt = null;

    public void UploadData(CardSimulator simulator, Upload mu) throws Exception {
        mu.sendData(psbt, (byte) AppletInstructions.CLASS_PSBT_UPLOAD, simulator);
    }

    public void UploadTransaction() throws Exception {
        mu.sendData(psbt, (byte) AppletInstructions.CLASS_PSBT_UPLOAD, simulator);
    }

    public void DownloadDebugV0() throws Exception {
        System.out.print("Parsing transaction of version: " + md.downloadVersion(simulator) + (" (-1 means unspecified)") + System.lineSeparator());
        System.out.print("And of size: " + md.downloadSize(simulator) + " bytes" + System.lineSeparator());
        short inps = md.downloadNumOfInp(simulator);
        short outs = md.downloadNumOfOut(simulator);
        System.out.print("Number of inputs: " + inps + System.lineSeparator());
        System.out.print("Number of outputs: " + outs + System.lineSeparator());

        System.out.print(Arrays.toString(md.downloadMap(simulator, AppletInstructions.CLASS_DOWNLOAD_GLOBAL_ALL, (byte) 0)) + System.lineSeparator());

        int i;

        for (i = 0; i < inps; i++){
            System.out.print("Input with index: " + i + System.lineSeparator());
            System.out.print(Arrays.toString(md.downloadInputV0(simulator, (byte) i)) + System.lineSeparator());
        }
        for (i = 0; i < outs; i++){
            System.out.print("Output with index: " + i + System.lineSeparator());
            System.out.print(Arrays.toString(md.downloadOutputV0(simulator, (byte) i)) + System.lineSeparator());
        }
    }


    public void AppletDebugV0() throws Exception {
        UploadTransaction();
        DownloadDebugV0();
    }

    public AppletControl(String psbt) throws Exception {
        simulator = new CardSimulator();
        AID appletAID = AIDUtil.create("F000000001");
        simulator.installApplet(appletAID, MainApplet.class);
        simulator.selectApplet(appletAID);
        this.psbt = fromHex(psbt);
    }

    public AppletControl(CardSimulator sim, String psbt) throws Exception {
        simulator = sim;
        AID appletAID = AIDUtil.create("F000000001");
        simulator.installApplet(appletAID, MainApplet.class);
        simulator.selectApplet(appletAID);
        this.psbt = fromHex(psbt);
    }

    public AppletControl(CardSimulator sim, byte[] psbt) throws Exception {
        simulator = sim;
        AID appletAID = AIDUtil.create("F000000001");
        simulator.installApplet(appletAID, MainApplet.class);
        simulator.selectApplet(appletAID);
        this.psbt = psbt;
    }

    public AppletControl(byte[] psbt) throws Exception {
        simulator = new CardSimulator();
        AID appletAID = AIDUtil.create("F000000001");
        simulator.installApplet(appletAID, MainApplet.class);
        simulator.selectApplet(appletAID);
        this.psbt = psbt;
    }

    public AppletControl() throws Exception {
    }

    public static byte[] fromHex(String hex){
        // ukradeno
        byte[] res = new byte[hex.length() / 2];

        for (int i = 0; i < res.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(hex.substring(index, index + 2), 16);
            res[i] = (byte) j;
        }

        return res;
    }
}




