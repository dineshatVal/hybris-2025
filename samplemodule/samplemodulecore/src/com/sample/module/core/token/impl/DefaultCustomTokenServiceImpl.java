package com.sample.module.core.token.impl;

import com.sample.module.core.token.CustomTokenService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class DefaultCustomTokenServiceImpl implements CustomTokenService {

    @Override
    public String generateToken(String input1, String input2, String input3) {
        try {
            String combined = input1 + ":" + input2 + ":" + input3;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(combined.getBytes(StandardCharsets.UTF_8));

            // Encode hash to Base64 (can also use hex if preferred)
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash).substring(0, 24); // trim to desired length
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
