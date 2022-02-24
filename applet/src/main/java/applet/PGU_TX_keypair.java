package applet;

//TODO delete this class
public class PGU_TX_keypair extends KeyPair {
    public void fill(short arrayIndex) {
        key.fill(arrayIndex);
        System.out.print("arrayIndex after keyfillup: " + arrayIndex + System.lineSeparator());
        value.fill((short) (arrayIndex + key.getSize()));
        System.out.print("arrayIndex after valueFillup: " + (arrayIndex + key.getSize()) + System.lineSeparator());
        //inputCount = getInputCount();
        //outputCount = getOutputCount(inputCount);
    }

    short getInputCount() {
        System.out.print("inputCounto: " + Tools.compactWeirdoInt((short) (value.start + 4)) + System.lineSeparator());
        return Tools.compactWeirdoInt((short) (value.start + 4));
    }

    short getOutputCount(short inputCount) {
        short i = 0;
        short bytesToIgnore = (short) (4 + Tools.byteSizeOfCWI(inputCount));
        while (i < inputCount) {
            bytesToIgnore += ignoreInput(bytesToIgnore);
            i++;
        }
        return Tools.compactWeirdoInt(bytesToIgnore);
    }

    short ignoreInput(short bytesIgnored) {
        short signature_scrip_size = Tools.compactWeirdoInt((short) (bytesIgnored + 36));
        System.out.print("signature_script_size = " + signature_scrip_size + System.lineSeparator());
        return (short) (signature_scrip_size + 40 + Tools.byteSizeOfCWI(signature_scrip_size));

    }

}
