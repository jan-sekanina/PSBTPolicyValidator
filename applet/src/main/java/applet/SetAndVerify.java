package applet;

import javacard.security.*;

public class SetAndVerify {
    static ECPublicKey publicKey;
    static Signature signEngine;

    public static void setPublicKey(byte[] publicKeyBytes, short publicKeySize) {
        publicKey = (ECPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_EC_FP_PUBLIC, (short) 256, false);
        signEngine = Signature.getInstance(Signature.ALG_ECDSA_SHA, false);

        publicKey.setFieldFP(SecP256k1.p, (short) 0, (short) SecP256k1.p.length);
        publicKey.setA(SecP256k1.a, (short) 0, (short) SecP256k1.a.length);
        publicKey.setB(SecP256k1.b, (short) 0, (short) SecP256k1.b.length);
        publicKey.setG(SecP256k1.G, (short) 0, (short) SecP256k1.G.length);
        publicKey.setR(SecP256k1.r, (short) 0, (short) SecP256k1.r.length);
        publicKey.setK((short) 1);
        publicKey.setW(publicKeyBytes, (short) 0, publicKeySize);
    }

    public static boolean verifyECDSA(byte[] msg, short msgSize, byte[] sign, short signLen) {
        signEngine.init(publicKey, Signature.MODE_VERIFY);
        return signEngine.verify(msg, (short) 0, msgSize, sign, (short) 0, signLen);
    }
}
