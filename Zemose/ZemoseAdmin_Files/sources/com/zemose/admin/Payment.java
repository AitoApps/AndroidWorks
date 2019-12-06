package com.zemose.admin;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Payment {
    public static String hashCal(String str) {
        byte[] hashseq = str.getBytes();
        StringBuilder hexString = new StringBuilder();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("SHA-512");
            algorithm.reset();
            algorithm.update(hashseq);
            for (byte aMessageDigest : algorithm.digest()) {
                String hex = Integer.toHexString(aMessageDigest & 255);
                if (hex.length() == 1) {
                    hexString.append("0");
                }
                hexString.append(hex);
            }
        } catch (NoSuchAlgorithmException e) {
        }
        return hexString.toString();
    }
}
