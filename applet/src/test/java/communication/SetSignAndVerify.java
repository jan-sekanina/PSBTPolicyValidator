package communication;

import applet.SecP256k1;
import javacard.security.*;

public class SetSignAndVerify {
    static ECPublicKey publicKey;
    static ECPrivateKey privateKey;
    static KeyPair keyPair;
    static Signature signEngine;

    public static void setPrivateKey(byte[] privateKeyBytes) {
        privateKey = (ECPrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_EC_FP_PRIVATE, (short) 256, false);
        signEngine = Signature.getInstance(Signature.ALG_ECDSA_SHA, false);

        privateKey.setFieldFP(SecP256k1.p, (short) 0, (short) SecP256k1.p.length);
        privateKey.setA(SecP256k1.a, (short) 0, (short) SecP256k1.a.length);
        privateKey.setB(SecP256k1.b, (short) 0, (short) SecP256k1.b.length);
        privateKey.setG(SecP256k1.G, (short) 0, (short) SecP256k1.G.length);
        privateKey.setR(SecP256k1.r, (short) 0, (short) SecP256k1.r.length);
        privateKey.setK((short) 1);
        privateKey.setS(privateKeyBytes, (short) 0, (short) privateKeyBytes.length);
    }

    public static void setPublicKey(byte[] publicKeyBytes) {
        publicKey = (ECPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_EC_FP_PUBLIC, (short) 256, false);
        signEngine = Signature.getInstance(Signature.ALG_ECDSA_SHA, false);

        publicKey.setFieldFP(SecP256k1.p, (short) 0, (short) SecP256k1.p.length);
        publicKey.setA(SecP256k1.a, (short) 0, (short) SecP256k1.a.length);
        publicKey.setB(SecP256k1.b, (short) 0, (short) SecP256k1.b.length);
        publicKey.setG(SecP256k1.G, (short) 0, (short) SecP256k1.G.length);
        publicKey.setR(SecP256k1.r, (short) 0, (short) SecP256k1.r.length);
        publicKey.setK((short) 1);
        publicKey.setW(publicKeyBytes, (short) 0, (short) publicKeyBytes.length);
    }

    public static void setBoth(byte[] privateKeyBytes, byte[] publicKeyBytes) {
        publicKey = (ECPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_EC_FP_PUBLIC, (short) 256, false);
        privateKey = (ECPrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_EC_FP_PRIVATE, (short) 256, false);
        signEngine = Signature.getInstance(Signature.ALG_ECDSA_SHA, false);

        privateKey.setFieldFP(SecP256k1.p, (short) 0, (short) SecP256k1.p.length);
        privateKey.setA(SecP256k1.a, (short) 0, (short) SecP256k1.a.length);
        privateKey.setB(SecP256k1.b, (short) 0, (short) SecP256k1.b.length);
        privateKey.setG(SecP256k1.G, (short) 0, (short) SecP256k1.G.length);
        privateKey.setR(SecP256k1.r, (short) 0, (short) SecP256k1.r.length);
        privateKey.setK((short) 1);
        privateKey.setS(privateKeyBytes, (short) 0, (short) privateKeyBytes.length);

        publicKey.setFieldFP(SecP256k1.p, (short) 0, (short) SecP256k1.p.length);
        publicKey.setA(SecP256k1.a, (short) 0, (short) SecP256k1.a.length);
        publicKey.setB(SecP256k1.b, (short) 0, (short) SecP256k1.b.length);
        publicKey.setG(SecP256k1.G, (short) 0, (short) SecP256k1.G.length);
        publicKey.setR(SecP256k1.r, (short) 0, (short) SecP256k1.r.length);
        publicKey.setK((short) 1);
        publicKey.setW(publicKeyBytes, (short) 0, (short) publicKeyBytes.length);
    }

    public static void genBoth() {
        publicKey = (ECPublicKey) KeyBuilder.buildKey(KeyBuilder.TYPE_EC_FP_PUBLIC, (short) 256, false);
        privateKey = (ECPrivateKey) KeyBuilder.buildKey(KeyBuilder.TYPE_EC_FP_PRIVATE, (short) 256, false);
        keyPair = new KeyPair(publicKey, privateKey);
        signEngine = Signature.getInstance(Signature.ALG_ECDSA_SHA, false);

        privateKey.setFieldFP(SecP256k1.p, (short) 0, (short) SecP256k1.p.length);
        privateKey.setA(SecP256k1.a, (short) 0, (short) SecP256k1.a.length);
        privateKey.setB(SecP256k1.b, (short) 0, (short) SecP256k1.b.length);
        privateKey.setG(SecP256k1.G, (short) 0, (short) SecP256k1.G.length);
        privateKey.setR(SecP256k1.r, (short) 0, (short) SecP256k1.r.length);
        privateKey.setK((short) 1);

        publicKey.setFieldFP(SecP256k1.p, (short) 0, (short) SecP256k1.p.length);
        publicKey.setA(SecP256k1.a, (short) 0, (short) SecP256k1.a.length);
        publicKey.setB(SecP256k1.b, (short) 0, (short) SecP256k1.b.length);
        publicKey.setG(SecP256k1.G, (short) 0, (short) SecP256k1.G.length);
        publicKey.setR(SecP256k1.r, (short) 0, (short) SecP256k1.r.length);
        publicKey.setK((short) 1);

        keyPair.genKeyPair();
    }

    public static boolean SignVerifyECDSA(byte[] msg, byte[] tmpSignArray) {
        signEngine.init(privateKey, Signature.MODE_SIGN);
        short signLen = signEngine.sign(msg, (short) 0, (short) msg.length, tmpSignArray, (short) 0);

        signEngine.init(publicKey, Signature.MODE_VERIFY);
        return signEngine.verify(msg, (short) 0, (short) msg.length, tmpSignArray, (short) 0, signLen);
    }

    public static short SignECDSA(byte[] msg, byte[] res) {
        signEngine.init(privateKey, Signature.MODE_SIGN);
        return signEngine.sign(msg, (short) 0, (short) msg.length, res, (short) 0);
    }

    public static boolean verifyECDSA(byte[] msg, short msgSize, byte[] sign, short signLen) {
        signEngine.init(publicKey, Signature.MODE_VERIFY);
        return signEngine.verify(msg, (short) 0, msgSize, sign, (short) 0, signLen);
    }
    //  bouncyCastle -- crypto v Jave
}
