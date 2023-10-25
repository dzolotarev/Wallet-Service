package org.example.wallet.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.ResourceBundle;

public class JwtHelper {

    public static String createJWT(String id) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .id(id)
                .issuedAt(now)
                .signWith(getSecret());
        long ttlMillis = getTTl();
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.expiration(exp);
        }
        return builder.compact();
    }

    public static Claims decodeJWT(String jwt) throws JwtException{
        Claims claims = null;
        try {
            try {
                claims = Jwts.parser().verifyWith(getSecret()).build().parseSignedClaims(jwt).getPayload();
            } catch (IllegalArgumentException e) {
                throw new RuntimeException(e);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return claims;
    }

    private static long getTTl() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jwt");
        return Long.parseLong(resourceBundle.getString("ttl"));
    }

    private static SecretKey getSecret() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle("jwt");
        String secret = resourceBundle.getString("secret");
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
