package cn.limw.summer.util;

import java.util.Random;

/**
 * @author li
 * @version 1 (2015年1月20日 上午10:53:13)
 * @since Java7
 */
public class Base64xEncrypter {
    public static String encrypt(String text, String key) {
        if (null == text) {
            text = "";
        }
        if (null == key) {
            key = "";
        }

        String random = String.valueOf(new Random().nextInt(32000));
        String encryptText = Md5Encrypter.encrypt32(random);
        int j = 0;
        String temp = "";
        char encryptTextArray[] = encryptText.toCharArray();
        char textChar[] = text.toCharArray();
        for (int i = 0; i < text.length(); i++) {
            j = j == encryptTextArray.length ? 0 : j;
            char c1 = textChar[i];
            char c2 = encryptTextArray[j++];
            char c3 = (char) (c1 ^ c2);
            char c4 = encryptTextArray[j - 1];
            temp += c4 + "" + c3;
        }
        return Base64Encrypter.encrypt(calculate(temp, key));
    }

    public static String decrypt(String encryptedText, String key) {
        encryptedText = calculate(Base64Encrypter.decrypt(encryptedText), key);
        if (encryptedText == null) {
            return null;
        }
        String text = "";
        char encryptedTextChar[] = encryptedText.toCharArray();
        for (int i = 0; i < encryptedText.length(); i++) {
            text += (char) (encryptedTextChar[i] ^ encryptedTextChar[++i]);
        }
        return text;
    }

    private static String calculate(String text, String key) {
        if (text == null) {
            return null;
        }
        key = Md5Encrypter.encrypt32(key);
        int j = 0;
        String temp = "";
        char encryptKeyChar[] = key.toCharArray();
        char textChar[] = text.toCharArray();
        for (int i = 0; i < text.length(); i++) {
            j = j == key.length() ? 0 : j;
            char c = (char) (textChar[i] ^ encryptKeyChar[j++]);
            temp = temp + c;
        }
        return temp;
    }
}