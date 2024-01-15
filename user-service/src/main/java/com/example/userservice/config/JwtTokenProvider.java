package com.example.userservice.config;

import com.example.userservice.config.exception.CustomException;
import com.example.userservice.vo.RequestLogin;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private Environment env;

//    @Value("${spring.jwt.token.secret-key}")
    private String secretKey;
//    @Value("${spring.jwt.token.expire-length}")
    private long validityInMilliseconds;

    public JwtTokenProvider(Environment env) {
        this.env = env;
        this.secretKey = env.getProperty("spring.jwt.token.secret-key");
        this.validityInMilliseconds = Long.parseLong(env.getProperty("spring.jwt.token.expire-length"));
    }

    //토큰생성
    public String createToken(String subject, RequestLogin requestLogin) {
        Claims claims = Jwts.claims().setSubject(subject);
        claims.put("PLATFORM", requestLogin.getPlatForm());
        claims.put("BROWSER", requestLogin.getBrowser());
        claims.put("CLIENT", requestLogin.getClient());
        claims.put("AUTH", requestLogin.getAuth());

        Date now = new Date();

        Date validity = new Date(now.getTime()
                + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    //토큰에서 값 추출
    public String getSubject(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception e) {
            throw new CustomException(String.format("유효하지 않은 토큰 [%s]", e.getMessage()));
        }
    }

    //유효한 토큰인지 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
