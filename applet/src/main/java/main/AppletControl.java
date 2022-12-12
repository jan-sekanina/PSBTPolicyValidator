package main;

import applet.AppletInstructions;
import applet.MainApplet;
import com.licel.jcardsim.base.CardManager;
import com.licel.jcardsim.smartcardio.CardSimulator;
import com.licel.jcardsim.utils.AIDUtil;
import javacard.framework.AID;

public class AppletControl {
    CardSimulator simulator = null;
    CardManager manager = null;
    Upload mu = new Upload();
    Download md = new Download();
    byte[] psbt = null;

    public void UploadData(CardSimulator simulator, Upload mu) throws Exception {
        mu.sendData(psbt, (byte) AppletInstructions.CLASS_PSBT_UPLOAD, simulator);
    }

    public void UploadTransaction() throws Exception {
        mu.sendData(psbt, (byte) AppletInstructions.CLASS_PSBT_UPLOAD, simulator);
    }

    public void DownloadDebug() throws Exception {
        System.out.print("Parsing transaction of version: " + md.downloadVersion(simulator) + System.lineSeparator());
        System.out.print("And of size: " + md.downloadSize(simulator) + " potatoes" + System.lineSeparator()); // TODO: this is weird
        short inps = md.downloadNumOfInp(simulator);
        short outs = md.downloadNumOfOut(simulator);
        System.out.print("Number of inputs: " + inps + System.lineSeparator());
        System.out.print("Number of outputs: " + outs + System.lineSeparator());

        //System.out.print(Arrays.toString(md.downloadMap(simulator, AppletInstructions.CLASS_DOWNLOAD_GLOBAL_ALL, (byte) 0)) + System.lineSeparator());

        int i;

        for (i = 0; i < inps; i++){
            System.out.print("Input with index: " + i + System.lineSeparator());
            System.out.print(bytesToHex((md.downloadInput(simulator, (byte) i))) + System.lineSeparator());
        }
        for (i = 0; i < outs; i++){
            System.out.print("Output with index: " + i + System.lineSeparator());
            System.out.print(bytesToHex((md.downloadOutput(simulator, (byte) i))) + System.lineSeparator());
        }
    }


    public void AppletDebug() throws Exception {
        UploadTransaction();
        DownloadDebug();
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
   public String bytesToHex(byte[] bytes) {
       StringBuilder res = new StringBuilder();
       int cb;

       for (byte aByte : bytes) {
           cb = aByte & 0xFF;
           res.append(Integer.toHexString(cb / 16));
           res.append(Integer.toHexString(cb % 16));
       }
    return res.toString();
    }
}




