package com.udacity.jwdnd.course1.cloudstorage.common;

import java.security.SecureRandom;
import java.util.Base64;

public class CommonUltis {

	// Validate input
    public static boolean isValidString(String input) {
        if (input == null || input.isBlank() || input.isEmpty()) {
            return false;
        }
        return true;
    }

    // Ramdom Encode using base64
    public static String ramdomEncodeBase64() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        return encodedKey;
    }
}
