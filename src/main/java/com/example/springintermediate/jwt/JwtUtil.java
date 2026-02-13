package com.example.springintermediate.jwt;

import com.example.springintermediate.entity.UserRoleEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_KEY = "auth";
    private static final String BEARER_PREFIX = "bearer ";
    private final Long TOKEN_TIME = 1000L * 60 * 60;

    @Value("${jwt.secret.key}")  // Base64 Encode 한 SecretKey
    private String secretKey;
    private Key key; //Decode된 Secret Key를 담는 객체
    private SignatureAlgorithm algorithm = SignatureAlgorithm.HS256;

    Logger logger = LoggerFactory.getLogger("JWT logger");

    @PostConstruct
    public void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String createJwtToken(String username, UserRoleEnum role) {
        Date date = new Date();
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(username) // 사용자 식별자값(ID)
                        .claim(AUTHORIZATION_KEY, role) // 사용자 권한
                        .setExpiration(new Date(date.getTime() + TOKEN_TIME)) // 만료 시간
                        .setIssuedAt(date) // 발급일
                        .signWith(key, algorithm) // 암호화 알고리즘
                        .compact();
    }

    public void addJwtToCookie(String token, HttpServletResponse res) {
        try {
            token = URLEncoder.encode(token, "utf-8").replaceAll("\\+", "%20");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage());
        }
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token); // Name-Value
        cookie.setPath("/");

        res.addCookie(cookie);
    }

    public String substringToken(String tokenValue) {
        //StringUtils.hasText를 사용하여 공백만 있는지 or null or 길이 0인지를 확인
        if (StringUtils.hasText(tokenValue) && tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(7);
        }
        logger.error("Not Found Token");
        throw new NullPointerException("Not Found Token");
    }

    public boolean validateToken(String token) {
//        Jwts.parserBuilder().setSigningKey(key)
//                .build()
//                .parseClaimsJwt(token);
//        return true;

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            //JWT가 위변조되지 않았는지 secretKey(key)값을 넣어 확인
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            logger.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    public Claims getUserInfoFromToken(String token) {
        //JWT의 구조 중 Payload 부분에는 토큰에 담긴 정보가 들어있음
        // 정보의 한 ‘조각’이 클레임(claim)
        //토큰에는 1개 이상의 클레임으로 구성
        return Jwts.parserBuilder().setSigningKey(key)
                .build().parseClaimsJws(token)
                .getBody();
    }
}
