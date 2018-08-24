package org.yejh.crypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;

public class DESCrypt {
    private static final String cryptType = "DES";
    private static final String key = "20180823";

    public static byte[] encrypt(byte[] source, String key) throws GeneralSecurityException {
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(cryptType);
        SecretKey secretKey = keyFactory.generateSecret(desKey);
        Cipher cipher = Cipher.getInstance(cryptType);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, random);
        return cipher.doFinal(source);
    }

    public static byte[] decrypt(byte[] source, String key) throws GeneralSecurityException {
        SecureRandom random = new SecureRandom();
        DESKeySpec desKey = new DESKeySpec(key.getBytes());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(cryptType);
        SecretKey secretKey = keyFactory.generateSecret(desKey);
        Cipher cipher = Cipher.getInstance(cryptType);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, random);
        return cipher.doFinal(source);
    }

    public static void main(String[] args) throws Exception {
        String plain = "中文English123";
        byte[] encodeByte = encrypt(plain.getBytes(), key);
        String encodeStr = new String(encodeByte);
        byte[] decodeByte = decrypt(encodeByte, key);
        String decodeStr = new String(decodeByte);
        System.out.println("encodeStr: " + encodeStr);
        System.out.println("decodeStr: " + decodeStr);
    }
}
