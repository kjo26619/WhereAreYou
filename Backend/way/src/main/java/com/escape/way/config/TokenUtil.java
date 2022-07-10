package com.escape.way.config;

import com.escape.way.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class TokenUtil {
    public final static long ACCESS_TOKEN_VALIDATION = 1000L * 60 * 2;
    public final static long REFRESH_TOKEN_VALIDATION = 1000L * 60 * 4;

    final static public String ACCESS_TOKEN = "accessToken";

    // @Value("${security.jwt.token.secret-key}")
    private static final String secretKey = "SecretKey";

    public String generateToken(User user, long expiration) {
        Map<String, Object> claims = new HashMap<>();

        JwtBuilder builder = Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUserId())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey);

        return builder.compact();
    }

    public String generateAccessToken(User user) {
        return generateToken(user, ACCESS_TOKEN_VALIDATION);
    }

    public String generateRefreshToken(User user) { return generateToken(user, REFRESH_TOKEN_VALIDATION); }

    public Boolean validateToken(String token, User user) {
        final String username = getUsernameFromToken(token);

        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}
