package ch.zli.m223.ksh20.user.security;

import ch.zli.m223.ksh20.user.roles.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private final String secret = "bMmE53i4Reqw6cSobBQN433N2yiu2s7NFvr8Xu9NR3Z4926T6UjGSVD7XerxnaM5tpvwgHrHf5Y4GMxDUWEbanJFs474tQPTwwfKvXzz3NUp7eXfthna7NzAEotBoPgy";

    private final long expirationMs = 86400000; // 24 hours

    public String generateJwtToken(String username, Role role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public String getRoleFromJwtToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        return claims.get("role", String.class);
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            // Invalid token
            return false;
        }
    }
}
