package main;

import com.licel.jcardsim.smartcardio.CardSimulator;

public class Run {
    public static void main(String[] args) throws Exception {
        // ./gradlew run --args="arg1 arg2 arg3"
        if (args.length < 1) {
            System.out.print("No transaction detected." + System.lineSeparator());
            System.out.print("run template: `./gradlew run --args=\"your_transaction\"`" + System.lineSeparator());
            return;
        }
        System.out.print("You entered a following transaction:" + System.lineSeparator());
        System.out.print(args[0] + System.lineSeparator());
        System.out.print(System.lineSeparator());

        CardSimulator simulator = new CardSimulator();

        AppletControl ap = new AppletControl(simulator, args[0]);
        ap.UploadTransaction();
        ap.DownloadDebug();
    }

}