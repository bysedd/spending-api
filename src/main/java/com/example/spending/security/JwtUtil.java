package com.example.spending.security;

import com.example.spending.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

@Component
public class JwtUtil {

    @Value("${auth.jwt.secret}")
    private String jwtSecret;

    @Value("${auth.jwt.expiration}")
    private long jwtExpirationMs;

    /**
     * Generates a JWT token for the provided authentication.
     *
     * @param authentication The authentication object containing the user details.
     * @return They generated JWT token, or null if there was an error generating the token.
     */
    public String generateToken(Authentication authentication) {
        Date expirationDate = new Date(new Date().getTime() + jwtExpirationMs);
        User user = (User) authentication.getPrincipal();

        try {
            Key secretKey = hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

            return Jwts.builder().subject(user.getUsername()).issuedAt(new Date()).expiration(expirationDate).signWith(secretKey).compact();
        } catch (Exception e) {
            Logger logger = org.slf4j.LoggerFactory.getLogger(JwtUtil.class);
            logger.error("Error generating JWT token", e);
            return null;
        }
    }

    /**
     * Extracts and verifies the claims (payload) from a given JWT token.
     *
     * @param token The JWT token to parse and extract the claims from.
     * @return The claims (payload) are parsed from the JWT token, or null if there was an error parsing the token.
     */
    private Claims getClaims(String token) {
        try {
            SecretKey secretKey = hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
            return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
        } catch (Exception e) {
            Logger logger = LoggerFactory.getLogger(JwtUtil.class);
            logger.error("Error parsing JWT token", e);
            return null;
        }
    }

    /**
     * Retrieves the email from a JWT token.
     *
     * @param token The JWT token from which to retrieve the email.
     * @return The email is extracted from the JWT token or null if the token is invalid or cannot be parsed.
     */
    public String getUsername(String token) {
        Claims claims = getClaims(token);

        if (claims == null) return null;

        return claims.getSubject();
    }

    /**
     * Validates a JWT token.
     *
     * @param token The JWT token to validate.
     * @return True if the token is valid and not expired, false otherwise.
     */
    public boolean validateToken(String token) {
        Claims claims = getClaims(token);

        if (claims == null) return false;

        String email = claims.getSubject();
        boolean isTokenExpired = claims.getExpiration().before(new Date());

        return email != null && !isTokenExpired;
    }
}
