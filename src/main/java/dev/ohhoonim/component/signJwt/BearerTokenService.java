package dev.ohhoonim.component.signJwt;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class BearerTokenService implements BearerTokenUsecase {
    Logger log = LoggerFactory.getLogger(BearerTokenService.class);
    private String keyChar = "e3f1a5c79b2d4f8e6a1c3d7f9b0e2a4c5d6f8a9b3c1d7e4f2a6b8c0d9e7f3a1";
    private SecretKey key;

    public BearerTokenService() {
        key = Keys.hmacShaKeyFor(keyChar.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateAccessToken(String userName, List<Authority> authorities) {
        return generateToken(userName, authorities, TokenType.ACCESS);
    }

    @Override
    public String generateRefreshToken(String userName, List<Authority> authorities) {
        return generateToken(userName, authorities, TokenType.REFRESH);
    }

    @Override
    public String getUsername(String refreshToken) {
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(refreshToken)
                .getPayload().getSubject();
    }

    @Override
    public Date getExpired(String refreshToken) {
        return Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(refreshToken)
                .getPayload().getExpiration();
    }

    @Override
    public String generateDenyToken(String userName, List<Authority> authorities) {
        return generateToken(userName, authorities, TokenType.DENY);
    }

    private String generateToken(String username,
            List<Authority> authorities,
            TokenType tokenType) {
        return Jwts.builder()
                .subject(username)
                .claim("roles",
                        authorities.stream()
                                .map(Authority::authority)
                                .collect(Collectors.joining(",")))
                .expiration(tokenType.ext())
                .issuedAt(Date.from(LocalDateTime.now().toInstant(ZoneOffset.of("+09:00"))))
                .signWith(key)
                .compact();
    }

    public enum TokenType {
        ACCESS(Date.from(LocalDateTime.now().plusHours(1).toInstant(ZoneOffset.of("+09:00")))),
        REFRESH(Date.from(LocalDateTime.now().plusDays(7).toInstant(ZoneOffset.of("+09:00")))),
        DENY(Date.from(LocalDateTime.now().plusDays(-7).toInstant(ZoneOffset.of("+09:00"))));

        private final Date exp;

        TokenType(Date exp) {
            this.exp = exp;
        }

        public Date ext() {
            return this.exp;
        }
    }

}