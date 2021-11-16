package applet;

public class PGU_TX_keypair extends KeyPair {
    short inputCount = -1;
    short outputCount = -1;

    public void fillUp(short arrayIndex, byte[] data) {
        key.fillUp(arrayIndex, data);
        value.fillUp((short) (arrayIndex + key.getSize()), data);
        inputCount = getInputCount();
        outputCount = getOutputCount(inputCount);
    }

    short getInputCount() {
        System.out.print("inputCounto: " + Tools.compactWeirdoInt((short) (value.start + 4), value.data) + System.lineSeparator());
        return Tools.compactWeirdoInt((short) (value.start + 4), value.data);
    }

    short getOutputCount(short inputCount) {
        short i = 0;
        short bytesToIgnore = 0;
        while (i < inputCount) {
            bytesToIgnore += ignoreInput(bytesToIgnore);
            i++;
        }
        return Tools.compactWeirdoInt(bytesToIgnore, value.data);
    }

    short ignoreInput(short bytesIgnored) {
        return (short) (40 + Tools.compactWeirdoInt((short) (bytesIgnored + 36), value.data));
    }

}
