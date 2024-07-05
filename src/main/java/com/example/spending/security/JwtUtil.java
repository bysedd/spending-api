package com.example.spending.security;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

import com.example.spending.domain.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${auth.jwt.secret}")
  private String jwtSecret;

  @Value("${auth.jwt.expiration}")
  private long jwtExpirationMs;

  private void log(String message, Exception e) {
    Logger logger = org.slf4j.LoggerFactory.getLogger(JwtUtil.class);
    logger.error(message, e);
  }

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

      return Jwts.builder()
          .subject(user.getUsername())
          .issuedAt(new Date())
          .expiration(expirationDate)
          .signWith(secretKey)
          .compact();
    } catch (Exception e) {
      log("Error generating JWT token", e);
      return null;
    }
  }

  /**
   * Extracts and verifies the claims (payload) from a given JWT token.
   *
   * @param token The JWT token to parse and extract the claims from.
   * @return The claims (payload) parsed from the JWT token or an empty map if there was an error
   *     parsing the token.
   */
  private Claims getClaims(String token) {
    try {
      SecretKey secretKey = hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
      return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    } catch (Exception e) {
      log("Error parsing JWT token", e);
      return (Claims) Jwts.claims();
    }
  }

  /**
   * Extracts the email from a JWT token.
   *
   * @param token The JWT token to extract the email from.
   * @return The email is extracted from the JWT token or null if there was an error parsing the
   *     token or the token is invalid.
   */
  public String getUsername(String token) {
    Claims claims = getClaims(token);

    if (claims == null) {
      return null;
    }

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

    if (claims == null) {
      return false;
    }

    String email = claims.getSubject();
    boolean isTokenExpired = claims.getExpiration().before(new Date());

    return email != null && !isTokenExpired;
  }
}
