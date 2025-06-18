package com.equities.equityplatform.util;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.security.Key;

import com.equities.equityplatform.service.MyService;
import io.jsonwebtoken.*;

import java.util.Date;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

public class AuthUtil {

    // Initialize local variables and objects
    static MyService service;
    public static long ttlMillis =  60 * 60 * 1000;
    public static SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    // Used to generate signing key
    private static Key getSigningKey() {
        byte[] keyBytes = service.getSecretKey().getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(keyBytes,signatureAlgorithm.getJcaName());
    }

    //Sample method to construct a JWT
    public static String createJWT(String id, String subject) {

        long currMillis = System.currentTimeMillis();
        Date now = new Date(currMillis);

        // Set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer("backend")
                .signWith(getSigningKey(), signatureAlgorithm);

        // If specified, add the expiration
        if (ttlMillis >= 0) {
            Date exp = new Date(currMillis + ttlMillis);
            builder.setExpiration(exp);
        }

        // Build JWT and serializes to URL-safe string
        return builder.compact();
    }

    public static Claims decodeJWT(String jwt) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (JwtException e) {
            System.out.println("JWT is invalid or expired: " + e.getMessage());
            return null;
        }
    }
}
