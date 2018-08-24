package org.yejh.crypt;

import java.util.Base64;

public class Base64Crypt {
    public static String encrypt(String source) {
        byte[] target = Base64.getEncoder().encode(source.getBytes());
        return new String(target);
    }

    public static String decrypt(String source) {
        byte[] target = Base64.getDecoder().decode(source.getBytes());
        return new String(target);
    }

    public static void main(String[] args) {
        String encodeStr = encrypt("中文English123");
        String decodeStr = decrypt(encodeStr);
        System.out.println("encodeStr: " + encodeStr);
        System.out.println("decodeStr: " + decodeStr);
    }
}
