package cn.limw.summer.security.rsa;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;

import cn.limw.summer.util.ArrayUtil;

/**
 * @author li
 * @version 1 (2014年11月6日 下午4:00:43)
 * @since Java7
 */
public class RsaUtil {
    private static final String RSA = "RSA";

    private static final String MD5_WITH_RSA = "MD5withRSA";

    /**
     * 生成一对 PublicKey PrivateKey
     */
    public static KeyPair keyPair() {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(RSA);
            keyPairGen.initialize(1024); //密钥位数
            return keyPairGen.generateKeyPair(); //密钥对
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 加密
     * @see #doEncrypt(Key, byte[])
     */
    public static byte[] encrypt(Key key, byte[] input) {
        byte[] result = new byte[0];
        for (int i = 0; i < input.length; i = i + 64) {
            result = ArrayUtil.append(result, doEncrypt(key, ArrayUtil.subArray(input, i, 64)));
        }
        return result;
    }

    /**
     * 解密
     * @see #doDecrypt(Key, byte[])
     */
    public static byte[] decrypt(Key key, byte[] input) {
        byte[] result = new byte[0];
        for (int i = 0; i < input.length; i = i + 128) {
            result = ArrayUtil.append(result, doDecrypt(key, ArrayUtil.subArray(input, i, 128)));
        }
        return result;
    }

    /**
     * @see #decrypt(Key, byte[])
     * @see #stringToPublicKey(String)
     * @see #decodeBase64(byte[])
     */
    public static String decryptByPublicKey(String publicKey, String input) {
        return new String(decrypt(stringToPublicKey(publicKey), decodeBase64(input.getBytes())));
    }

    /**
     * 加密
     * @param input Data must not be longer than 117 bytes
     */
    private static byte[] doEncrypt(Key key, byte[] input) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);//Cipher.getInstance("RSA/ECB/PKCS1Padding");        //加解密类
            cipher.init(Cipher.ENCRYPT_MODE, key); //加密
            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     * @param input Data must not be longer than 117 bytes
     */
    private static byte[] doDecrypt(Key key, byte[] input) {
        try {
            Cipher cipher = Cipher.getInstance(RSA);//Cipher.getInstance("RSA/ECB/PKCS1Padding");        //加解密类
            cipher.init(Cipher.DECRYPT_MODE, key); //加密
            return cipher.doFinal(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 签名
     */
    public static byte[] sign(PrivateKey privateKey, byte[] input) {
        try {
            Signature instance = Signature.getInstance(MD5_WITH_RSA);
            instance.initSign(privateKey);
            instance.update(input);
            return instance.sign();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 验证签名
     */
    public static Boolean verify(PublicKey publicKey, byte[] input, byte[] signature) {
        try {
            Signature instance = Signature.getInstance(MD5_WITH_RSA);
            instance.initVerify(publicKey);
            instance.update(input);
            return instance.verify(signature);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see #verify(PublicKey, byte[], byte[])
     * @see #stringToPublicKey(String)
     * @see #decodeBase64(byte[])
     */
    public static Boolean verify(String publicKey, String input, String signature) {
        return verify(stringToPublicKey(publicKey), decodeBase64(input.getBytes()), decodeBase64(signature.getBytes()));
    }

    /**
     * encode
     */
    public static byte[] encodeBase64(byte[] input) {
        return new Base64().encode(input);
    }

    /**
     * decode
     */
    public static byte[] decodeBase64(byte[] input) {
        return new Base64().decode(input);
    }

    /**
     * String to PublicKey
     * @see #decodeBase64(byte[])
     */
    public static PublicKey stringToPublicKey(String pubKey) {
        try {
            byte[] decode = decodeBase64(pubKey.getBytes());
            return KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(decode));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * String to PrivateKey
     * @see #encodeBase64(byte[])
     */
    public static PrivateKey stringToPrivateKey(String priKey) {
        try {
            byte[] decode = decodeBase64(priKey.getBytes());
            return KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(decode));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Key to String
     * @see #encodeBase64(byte[])
     */
    public static String keyToString(Key key) {
        return new String(encodeBase64(key.getEncoded()));
    }

    /**
     * @see #sign(PrivateKey, String)
     * @see #encrypt(Key, byte[])
     * @see #encodeBase64(byte[])
     */
    public static String encrypt(Key key, String input) {
        byte[] bytes = input.getBytes();
        byte[] encrypt = encrypt(key, bytes);
        byte[] encodeBase64 = encodeBase64(encrypt);
        return new String(encodeBase64);
    }

    /**
     * @see #encrypt(Key, String)
     * @see #decodeBase64(byte[])
     * @see #encodeBase64(byte[])
     */
    public static String sign(PrivateKey privateKey, String input) {
        byte[] bytes = input.getBytes();
        byte[] decodeBase64 = decodeBase64(bytes);

        byte[] sign = sign(privateKey, decodeBase64);
        byte[] encodeBase64 = encodeBase64(sign);
        return new String(encodeBase64);
    }
}