package com.taskmanager.authenticator.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private static final String accessTokenType = "access";
    private static final String refreshTokenType = "refresh";

    private final SecurityProperties properties;

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        return claimsResolver.apply(getAllClaimsFromToken(token));
    }

    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(properties.getSecret())
                .parseClaimsJws(token)
                .getBody();
    }

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * Generate token for user specified by UserDetails
     * @param userDetails User information
     * @return Generated token
     */
    public String generateToken(UserDetails userDetails) {
        return doGenerateToken(userDetails, accessTokenType,
                properties.getTokenLifetime());
    }

    /**
     * Generate refresh token for user specified by UserDetails
     * @param userDetails User information
     * @return Generated token
     */
    public String generateRefreshToken(UserDetails userDetails) {
        return doGenerateToken(userDetails, refreshTokenType,
                properties.getRefreshTokenLifetime());
    }

    private String doGenerateToken(UserDetails userDetails,
                                   String type,
                                   Duration tokenTTL) {
        return Jwts.builder()
                .claim("type", type)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(Date.from(
                        Instant.now().plus(tokenTTL))
                )
                .signWith(SignatureAlgorithm.HS512, properties.getSecret()).compact();
    }

    /**
     * Validate received token & check if it matches with user details
     * @param token Token data
     * @param userDetails User information
     * @return true if token is valid, otherwise false
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        final Object type = getClaimFromToken(token, claims -> claims.get("type"));
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token) &&
                accessTokenType.equals(type);
    }

    /**
     * Validate received refresh token & check if it matches with user details
     * @param token Token data
     * @param userDetails User information
     * @return true if token is valid, otherwise false
     */
    public boolean validateRefreshToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        final Object type = getClaimFromToken(token, claims -> claims.get("type"));
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token) &&
                refreshTokenType.equals(type);
    }
}
