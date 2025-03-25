package com.github.zlamb1;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

public class HashService {
    public static Hash createHash(String string) throws NoSuchAlgorithmException, InvalidKeySpecException {
        Hash hash = new Hash();
        SecureRandom random = new SecureRandom();
        hash.salt = new byte[16];
        random.nextBytes(hash.salt);
        KeySpec spec = new PBEKeySpec(string.toCharArray(), hash.salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        hash.data = factory.generateSecret(spec).getEncoded();
        return hash;
    }

    public static boolean compareHash(Hash hash, String string) throws NoSuchAlgorithmException, InvalidKeySpecException {
        KeySpec spec = new PBEKeySpec(string.toCharArray(), hash.salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        byte[] data = factory.generateSecret(spec).getEncoded();
        if (data == null)
            return false;
        return Arrays.equals(hash.data, data);
    }
}
