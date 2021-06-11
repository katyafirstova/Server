package model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Hasher {
    static final Logger LOG = LoggerFactory.getLogger(Hasher.class);
    String passwordToHash;
    String salt;

    public static String getHash(String password) {
        String hash = null;
        Hasher hasher = new Hasher();
        try {
            hash = hasher.getSecurePassword(password, hasher.getSalt());
        } catch (NoSuchAlgorithmException e) {
            LOG.debug(e.getLocalizedMessage());
        }
        return hash;
    }

    public String getSecurePassword(String password, byte[] salt) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] bytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }

    public byte[] getSalt() throws NoSuchAlgorithmException {
        return "123456789".getBytes(StandardCharsets.UTF_8);
    }

    public static void main(String[] args) {
        System.out.println(Hasher.getHash("123456"));
        System.out.println(Hasher.getHash("123456"));
        System.out.println(Hasher.getHash("123456"));
    }
}
