package com.example.jabits_homework.jwt;

import com.example.jabits_homework.entity.token.repository.TokenRepository;
import com.example.jabits_homework.error.exceptions.InvalidTokenException;
import com.example.jabits_homework.error.exceptions.IsNotRefreshTokenException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${jwt.secret.key}")
    private String secretKey;

    @Value("${jwt.access}")
    private Long accessExpire;

    @Value("${jwt.refresh}")
    private Long refreshExpire;

    private final TokenRepository tokenRepository;

    public String generateAccessToken(Integer userId) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + accessExpire * 1000))
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .claim("type", "access")
                .compact();
    }

    public String generateRefreshToken(Integer userId) {
        return Jwts.builder()
                .setExpiration(new Date(System.currentTimeMillis() + refreshExpire * 1000))
                .setIssuedAt(new Date())
                .setSubject(userId.toString())
                .claim("type", "refresh")
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey)
                    .parseClaimsJws(token).getBody().getSubject();
            return true;
        }catch (Exception e) {
            return false;
        }
    }

    public boolean isRefreshToken(String token) {
        if (validateToken(token)) {
            tokenRepository.deleteByRefreshToken(token);
            throw new IsNotRefreshTokenException();
        }

        return Jwts.parser().setSigningKey(secretKey)
                .parseClaimsJws(token).getBody().get("type").equals("refresh_token");
    }

    public Integer getUserId(String token) {
        System.out.println(token);
        if(!validateToken(token))
            throw new InvalidTokenException();

        return Integer.parseInt(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject());
    }
}
