package com.smartInventory.backend.security;

import com.smartInventory.backend.enums.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "0c9031d3e3130c59cca7cf4eac9dafddf4ba0a264c6463ebdd8a8c4774cbc7ce"; // 🔥 Use at least 256-bit key
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 10; // 10 hours

    private Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    //generate jwt token
    public String generateToken(String username , Role role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    //extract the username from token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //extract expiration date from token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    //extract clams from token
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    //validate token
    public boolean validateToken(String token, String username) {
        return (extractUsername(token).equals(username) && !isTokenExpired(token));
    }

    //check if token is expired
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
}
