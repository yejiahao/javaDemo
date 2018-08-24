package org.yejh.crypt;

import java.security.MessageDigest;

public class MD5Crypt {
    public static String encrypt(String source) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(source.getBytes());
        byte[] digest = md.digest();
        final String HEX = "0123456789abcdef";
        StringBuilder sb = new StringBuilder(digest.length * 2);
        for (byte b : digest) {
            sb.append(HEX.charAt((b >> 4) & 0x0f));
            sb.append(HEX.charAt(b & 0x0f));
        }
        return sb.toString();
    }

    public static void main(String[] args) throws Exception {
        String encodeStr = encrypt("中文English123");
        System.out.println("encodeStr: " + encodeStr);
    }
}
