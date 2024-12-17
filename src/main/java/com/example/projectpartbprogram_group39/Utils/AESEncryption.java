package com.example.projectpartbprogram_group39.Utils;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;


public class AESEncryption {
    private static final String ALGORITHM = "AES";
    private static final int KEY_SIZE = 16;
    private static final String DEFAULT_KEY = "1234567890123456";

    private String keyValue;

    public AESEncryption(String key) {
        if (key.length() != KEY_SIZE) {
            throw new IllegalArgumentException("Key must be 16 characters long.");
        }
        this.keyValue = key;
    }

    private Key generateKey() {
        return new SecretKeySpec(keyValue.getBytes(), ALGORITHM);
    }

    public String encrypt(String password) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedBytes = cipher.doFinal(password.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public String decrypt(String encryptedPassword) throws Exception {
        Key key = generateKey();
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decodedBytes = Base64.getDecoder().decode(encryptedPassword);
        byte[] decryptedBytes = cipher.doFinal(decodedBytes);
        return new String(decryptedBytes);
    }

    public static AESEncryption withDefaultKey() {
        return new AESEncryption(DEFAULT_KEY);
    }
}