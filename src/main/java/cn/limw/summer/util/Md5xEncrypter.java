package cn.limw.summer.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 扩展的MD5加密器
 * @author jianglei
 */
public class Md5xEncrypter {
    /**
     * 密文长度
     */
    public static final int ENCRYPTED_TEXT_LENGTH = 64;

    /**
     * MD5加密长度
     */
    public static final int MD5_ENCRYPT_LENGTH = 32;

    protected final int staticKey;

    public Md5xEncrypter(int staticKey) {
        if (staticKey < 0 || staticKey > 31) {
            throw new IllegalArgumentException("The argument 'staticKey' must between [0, 31]");
        }
        this.staticKey = staticKey;
    }

    public String encryptByMd5Source(String md5Source, Object secretKey) {
        return encryptByMd5Source(md5Source, secretKey, this.staticKey);
    }

    public String encrypt(Object source, Object secretKey) {
        String md5Source = Md5Encrypter.encrypt32(source);
        return encryptByMd5Source(md5Source, secretKey);
    }

    public boolean validate(String encryptedText, Object source, Object secretKey) {
        String md5Source = Md5Encrypter.encrypt32(source);
        return validateByMd5Source(encryptedText, md5Source, secretKey);
    }

    private Integer[] getMd5SourceCharIndexes(int staticKey, int maxIndex) {
        char[] c = Md5Encrypter.encrypt32(staticKey).toCharArray();
        int length = c.length;
        List<Integer> list = new ArrayList<Integer>(length);
        for (int i = 0; i < length; i++) {
            int value;
            if (i == 0) {
                value = c[i] % maxIndex;
            } else {
                value = (list.get(i - 1) + c[i] % maxIndex) % maxIndex;
                while (list.contains(value)) {
                    if (value < maxIndex - 1) {
                        value++;
                    } else {
                        value = 0;
                    }
                }
            }
            list.add(value);
        }
        return list.toArray(new Integer[length]);
    }

    protected String encryptByMd5Source(String md5Source, Object secretKey, int staticKey) {
        if (secretKey == null) {
            secretKey = "";
        }
        String keyMd5 = Md5Encrypter.encrypt32(staticKey + secretKey.toString());
        char[] keyChars = keyMd5.toCharArray();
        char[] sourceChars = md5Source.toLowerCase().toCharArray();
        int length = keyChars.length + sourceChars.length;
        char[] c = new char[length];
        Integer[] sourceCharIndexes = getMd5SourceCharIndexes(staticKey, length);
        for (int i = 0; i < sourceChars.length; i++) {
            c[sourceCharIndexes[i]] = sourceChars[i];
        }
        int index = 0;
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 0) {
                c[i] = keyChars[index++];
            }
        }
        return new String(c);
    }

    public boolean validateByMd5Source(String encryptedText, String md5Source, Object secretKey) {
        if (md5Source.length() != MD5_ENCRYPT_LENGTH) {
            return false;
        }
        String encrptedResult = encryptByMd5Source(md5Source, secretKey, this.staticKey);
        return encryptedText.equalsIgnoreCase(encrptedResult);
    }

    public String getMd5Source(String encryptedText) {
        if (encryptedText.length() != ENCRYPTED_TEXT_LENGTH) {
            throw new IllegalArgumentException("The length of encrypted text must be " + ENCRYPTED_TEXT_LENGTH);
        }
        Integer[] indexes = getMd5SourceCharIndexes(this.staticKey, encryptedText.length());
        char[] c = new char[indexes.length];
        for (int i = 0; i < indexes.length; i++) {
            c[i] = encryptedText.charAt(indexes[i]);
        }
        return new String(c);
    }
}