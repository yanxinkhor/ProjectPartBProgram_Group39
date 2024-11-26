package com.example.projectpartbprogram_group39.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encryption {

    public static String hashPassword(String password){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashedMessage = messageDigest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hashedMessage) {

                String hex = Integer.toHexString(b & 0xff );
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);

            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
