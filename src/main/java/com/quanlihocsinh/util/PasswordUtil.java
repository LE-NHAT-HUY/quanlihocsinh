package com.quanlihocsinh.util;

import org.mindrot.jbcrypt.BCrypt;

import java.security.SecureRandom;

public final class PasswordUtil {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String ALL_CHARS = LETTERS + DIGITS;
    private static final SecureRandom RANDOM = new SecureRandom();

    private PasswordUtil() {
    }

    public static String generateRandomPassword(int length) {
        if (length < 8) {
            length = 8;
        }

        while (true) {
            StringBuilder builder = new StringBuilder(length);
            boolean hasLetter = false;
            boolean hasDigit = false;

            for (int i = 0; i < length; i++) {
                char ch = ALL_CHARS.charAt(RANDOM.nextInt(ALL_CHARS.length()));
                if (Character.isLetter(ch)) {
                    hasLetter = true;
                } else if (Character.isDigit(ch)) {
                    hasDigit = true;
                }
                builder.append(ch);
            }

            if (hasLetter && hasDigit) {
                return builder.toString();
            }
        }
    }

    public static String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt(12));
    }

    public static boolean matches(String rawPassword, String storedPassword) {
        if (rawPassword == null || storedPassword == null) {
            return false;
        }

        try {
            return BCrypt.checkpw(rawPassword, storedPassword);
        } catch (IllegalArgumentException ex) {
            return rawPassword.equals(storedPassword);
        }
    }
}