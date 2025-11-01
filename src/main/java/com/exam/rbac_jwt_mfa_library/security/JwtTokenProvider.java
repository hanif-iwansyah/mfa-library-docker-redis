package com.exam.rbac_jwt_mfa_library.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Duration;
import java.util.Date;
import java.util.Map;

import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final String issuer;
    private final Duration accessTokenTtl;
    private final Duration refreshTokenttl;

    public JwtTokenProvider(
            @Value("${jwt.secret}") @NotNull String secret,
            @Value("${jwt.issuer}") String issuer,
            @Value("${jwt.access-token-ttl}") String accessTokenTtl,
            @Value("${jwt.refresh-token-ttl}") String refreshTokenttl
    ) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.issuer = issuer;
        this.accessTokenTtl = Duration.parse(accessTokenTtl);
        this.refreshTokenttl = Duration.parse(refreshTokenttl);
    }

    public String createToken(String subject, Map<String, Object> claims) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + accessTokenTtl.toMillis());
        return Jwts.builder()
                .setSubject(subject)
                .setIssuer(issuer)
                .addClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Jws<Claims> parse(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

}
