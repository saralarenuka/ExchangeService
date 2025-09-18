package com.api.security;

import java.security.Key;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    private final Key signingKey;

    public JwtUtil(@Value("${jwt.secret}") String secret) {
        // Convert the secret to Base64, then decode back to bytes to ensure 32-byte minimum
        String encoded = io.jsonwebtoken.io.Encoders.BASE64.encode(secret.getBytes());
        this.signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(encoded));
    }

    /** Extract username (subject) */
    public String extractUsername(String token) {
        return extractAllClaims(token).getSubject();
    }

    /** Extract roles claim safely */
    public List<String> extractRoles(String token) {
        Object rolesClaim = extractAllClaims(token).get("roles");
        if (rolesClaim instanceof List<?>) {
            return ((List<?>) rolesClaim).stream().map(Object::toString).toList();
        } else if (rolesClaim instanceof String s) {
            // handle single role stored as String
            return List.of(s);
        }
        return List.of();
    }

    /** Validate username + expiration */
    public boolean validateToken(String token, String username) {
        return extractUsername(token).equals(username) && !isTokenExpired(token);
    }

    /** Basic validate: checks signature + expiry (no username needed) */
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token); // will throw if signature invalid or expired
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    private boolean isTokenExpired(String token) {
        Date exp = extractAllClaims(token).getExpiration();
        return exp.before(new Date());
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
