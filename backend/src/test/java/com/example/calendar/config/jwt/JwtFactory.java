package com.example.calendar.config.jwt;

import static java.util.Collections.emptyMap;

import com.example.calendar.accounts.jwt.JwtProperties; // Your JwtProperties
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm; // Note: Still used for builder
import io.jsonwebtoken.security.Keys; // Import Keys class
import java.security.Key; // Import Key interface
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtFactory {
    private String subject = "test@email.com";
    private Date issuedAt = new Date();
    private Date expiration = new Date(new Date().getTime() + Duration.ofDays(14).toMillis());
    private Map<String, Object> claims = emptyMap();

    @Builder
    public JwtFactory(String subject, Date issuedAt, Date expiration, Map<String, Object> claims) {
        this.subject = subject != null ? subject : this.subject;
        this.issuedAt = issuedAt != null ? issuedAt : this.issuedAt;
        this.expiration = expiration != null ? expiration : this.expiration;
        this.claims = claims != null ? claims : this.claims;
    }

    public static JwtFactory withDefaultValues() {
        return JwtFactory.builder().build();
    }

    /**
     * 지정된 JwtProperties를 사용하여 JWT 토큰을 생성합니다.
     *
     * @param jwtProperties JWT 설정 정보를 담고 있는 객체
     * @return 생성된 JWT 토큰 문자열
     */
    public String createToken(JwtProperties jwtProperties) {
        // JwtProperties에서 secretKey를 가져와 Key 객체로 변환
        // 테스트 코드이므로 매번 변환해도 무방하나, 실제 프로덕션 코드에서는 미리 생성해두는 것이 좋습니다.
        Key secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecretKey().getBytes());

        return Jwts.builder()
            .setSubject(subject)
            .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
            .setIssuer(jwtProperties.getIssuer())
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .addClaims(claims)
            .signWith(secretKey, SignatureAlgorithm.HS256) // Key 객체와 알고리즘으로 서명
            .compact();
    }
}