package main;


import applet.AppletInstructions;
import applet.MainApplet;
import com.licel.jcardsim.smartcardio.CardSimulator;
import com.licel.jcardsim.utils.AIDUtil;
import javacard.framework.AID;

import static main.Tools.fromHex;

public class Run {
    public static void main(String[] args) throws Exception {
        // ./gradlew run --args="arg1 arg2 arg3"
        System.out.println("Showcase:");

        Download download = new Download();
        Upload upload = new Upload();
        CardSimulator simulator = new CardSimulator();
        AID appletAID = AIDUtil.create("F000000001");
        simulator.installApplet(appletAID, MainApplet.class);
        simulator.selectApplet(appletAID);

        byte[] secret = fromHex("deadbeef");
        byte[] secret2 = fromHex("beefdead");

        byte[] transaction = fromHex(TransactionsImported.validTransaction1);
        byte[] transaction2 = fromHex(TransactionsImported.validTransaction2);
        String pol = "0a000d0a010d0402";
        byte[] policy = fromHex(pol); // policy checks signed transaction and a secret

        System.out.println("Policy to be used:");
        System.out.println(pol);
        System.out.println("Checking secret(2B) + and(1B) + checking another secret(2B) + and(1B) + transaction must have at least two inputs(2B)");
        System.out.println();


        System.out.println("Uploading data to check against secrets.");
        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADDITIONAL_DATA_UPLOAD, (byte) 0, (byte) 0, simulator);
        upload.sendData(secret2, (byte) AppletInstructions.CLASS_ADDITIONAL_DATA_UPLOAD, (byte) 1, (byte) 0, simulator);

        System.out.println("Uploading policy. Policy is locked.");
        upload.sendData(policy, (byte) AppletInstructions.CLASS_POLICY_UPLOAD, simulator);
        System.out.println("Result of validation without uploading additional data: " + download.downloadValidation(simulator));

        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADDITIONAL_DATA_UPLOAD, (byte) 0, (byte) 0, simulator);
        System.out.println("Result of validation after uploading the first secret only: " + download.downloadValidation(simulator));

        upload.sendData(secret2, (byte) AppletInstructions.CLASS_ADDITIONAL_DATA_UPLOAD, (byte) 1, (byte) 0, simulator);
        System.out.println("Result of validation after uploading the second secret only: " + download.downloadValidation(simulator));

        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADDITIONAL_DATA_UPLOAD, (byte) 0, (byte) 0, simulator);
        upload.sendData(secret2, (byte) AppletInstructions.CLASS_ADDITIONAL_DATA_UPLOAD, (byte) 1, (byte) 0, simulator);
        upload.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UPLOAD, simulator);
        System.out.println("Result of validation after uploading both secrets and transaction with one input: " + download.downloadValidation(simulator));

        upload.sendData(secret, (byte) AppletInstructions.CLASS_ADDITIONAL_DATA_UPLOAD, (byte) 0, (byte) 0, simulator);
        upload.sendData(secret2, (byte) AppletInstructions.CLASS_ADDITIONAL_DATA_UPLOAD, (byte) 1, (byte) 0, simulator);
        upload.sendData(transaction2, (byte) AppletInstructions.CLASS_PSBT_UPLOAD, simulator);

        System.out.println("Result of validation after uploading both secrets and transaction with two inputs: "
        + download.downloadValidation(simulator));
    }
}