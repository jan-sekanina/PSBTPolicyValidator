package applet;

public class PGU_TX_keypair extends KeyPair {
    Short inputCount = null;
    Short outputCount = null;

    public void fillUp(short arrayIndex, byte[] data) {
        key.fillUp(arrayIndex, data);
        System.out.print("arrayIndex after keyfillup: " + arrayIndex + System.lineSeparator());
        value.fillUp((short) (arrayIndex + key.getSize()), data);
        System.out.print("arrayIndex after valueFillup: " + (arrayIndex + key.getSize()) + System.lineSeparator());
        inputCount = getInputCount();
        outputCount = getOutputCount(inputCount);
    }

    short getInputCount() {
        System.out.print("inputCounto: " + Tools.compactWeirdoInt((short) (value.start + 4), value.data) + System.lineSeparator());
        return Tools.compactWeirdoInt((short) (value.start + 4), value.data);
    }

    short getOutputCount(short inputCount) {
        short i = 0;
        short bytesToIgnore = (short) (4 + Tools.byteSizeOfCWI(inputCount));
        while (i < inputCount) {
            bytesToIgnore += ignoreInput(bytesToIgnore);
            i++;
        }
        return Tools.compactWeirdoInt(bytesToIgnore, value.data);
    }

    short ignoreInput(short bytesIgnored) {
        short signature_scrip_size = Tools.compactWeirdoInt((short) (bytesIgnored + 36), value.data);
        System.out.print("signature_script_size = " + signature_scrip_size + System.lineSeparator());
        return (short) (signature_scrip_size + 40 + Tools.byteSizeOfCWI(signature_scrip_size));

    }

}
