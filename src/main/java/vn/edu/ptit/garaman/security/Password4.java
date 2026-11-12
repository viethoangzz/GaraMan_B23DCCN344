package vn.edu.ptit.garaman.security;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public final class Password4 {
    private static final int ITERATIONS = 120_000;
    private Password4() {}
    public static String hash(String value) {
        byte[] salt = new byte[16]; new SecureRandom().nextBytes(salt);
        return "pbkdf2$" + ITERATIONS + "$" + Base64.getEncoder().encodeToString(salt)
            + "$" + Base64.getEncoder().encodeToString(derive(value.toCharArray(), salt, ITERATIONS));
    }
    public static boolean verify(String value, String stored) {
        try {
            String[] parts = stored.split("\\$");
            if (parts.length != 4 || !parts[0].equals("pbkdf2")) return false;
            int iterations = Integer.parseInt(parts[1]);
            if (iterations < 10_000 || iterations > 500_000) return false;
            byte[] salt = Base64.getDecoder().decode(parts[2]);
            byte[] expected = Base64.getDecoder().decode(parts[3]);
            return MessageDigest.isEqual(expected, derive(value.toCharArray(), salt, iterations));
        } catch (RuntimeException e) { return false; }
    }
    private static byte[] derive(char[] chars, byte[] salt, int iterations) {
        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 256);
        try { return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded(); }
        catch (Exception e) { throw new IllegalStateException(e); }
        finally { spec.clearPassword(); }
    }
}
