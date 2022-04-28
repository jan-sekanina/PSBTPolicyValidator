package tests;

import applet.AppletInstructions;

public class ImpTrxUnitTests {

    Upload mu = new Upload();

    void transactionTestTemplate(byte expectedInput, byte expectedOutput, byte[] transaction) throws Exception {
        byte[] response = mu.sendData(transaction, (byte) AppletInstructions.CLASS_PSBT_UPLOAD);
        assert response[0] == expectedInput;
        assert response[1] == expectedOutput;
        assert response[2] == (byte) 42;
        assert response[3] == (byte) 69;
    }

    void runAllTests() throws Exception {
        transactionTestTemplate((byte) 1, (byte) 2, MyTests.fromHex(TransactionsImported.validTransaction1));
        // Case: PSBT with one P2PKH input. Outputs are empty

        transactionTestTemplate((byte) 2, (byte) 2, MyTests.fromHex(TransactionsImported.validTransaction2));
        // Case: PSBT with one P2PKH input and one P2SH-P2WPKH input. First input is signed and finalized. Outputs are empty

        transactionTestTemplate((byte) 1, (byte) 2, MyTests.fromHex(TransactionsImported.validTransaction3));
        // Case: PSBT with one P2PKH input which has a non-final scriptSig and has a sighash type specified. Outputs are empty

        transactionTestTemplate((byte) 2, (byte) 2, MyTests.fromHex(TransactionsImported.validTransaction4));
        // Case: PSBT with one P2PKH input and one P2SH-P2WPKH input both with non-final scriptSigs. P2SH-P2WPKH input's redeemScript is available. Outputs filled.

        transactionTestTemplate((byte) 1, (byte) 1, MyTests.fromHex(TransactionsImported.validTransaction5));
        // Case: PSBT with one P2SH-P2WSH input of a 2-of-2 multisig, redeemScript, witnessScript, and keypaths are available. Contains one signature.

        transactionTestTemplate((byte) 1, (byte) 1, MyTests.fromHex(TransactionsImported.validTransaction6));
        // Case: PSBT with one P2WSH input of a 2-of-2 multisig. witnessScript, keypaths, and global xpubs are available. Contains no signatures. Outputs filled.

        transactionTestTemplate((byte) 1, (byte) 1, MyTests.fromHex(TransactionsImported.validTransaction7));
        // Case: PSBT with unknown types in the inputs.

        transactionTestTemplate((byte) 2, (byte) 2, MyTests.fromHex(TransactionsImported.validTransaction8));
        // Case: PSBT with `PSBT_GLOBAL_XPUB`

        transactionTestTemplate((byte) 0, (byte) 0, MyTests.fromHex(TransactionsImported.validTransaction9));
        // Case: PSBT with global unsigned tx that has 0 inputs and 0 outputs

        transactionTestTemplate((byte) 0, (byte) 2, MyTests.fromHex(TransactionsImported.validTransaction10));
        // Case: PSBT with 0 inputs
    }
}
