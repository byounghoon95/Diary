package com.example.diary.component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider /*implements InitializingBean*/ {
//    private static final String AUTHORITIES_KEY = "NeighborAPI";
    private final String secretKey;
    private final long expiredMs;
    //위 final에 @value를 붙혀주면 String or long 빈이 없다고 나오면서 에러가 발생
    public TokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expired-ms}") long expiredMs) {
        this.secretKey = secretKey;
        this.expiredMs = expiredMs;
    }

    //    private Key key;
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        byte[] keyBytes = Base64.getDecoder().decode(secretKey);
//        this.key = Keys.hmacShaKeyFor(keyBytes);
//    }



    /* before(new Date()) 는 토큰이 만료된게 현재보다 이전이면 토큰은 만료된 것으로 처리 */
    public boolean isExpired(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token)
                .getBody().getExpiration().before(new Date());
    }

    /**인증 정보 조회 */
//    public Authentication getAuthentication(String token) {
//        Claims claims = Jwts.parser()
//                .setSigningKey(secretKey)
//                .parseClaimsJws(token)
//                .getBody();
//
//        User principal = new User(claims.getSubject(), "", AUTHORITIES_KEY);
//        return new UsernamePasswordAuthenticationToken(principal, token, AUTHORITIES_KEY);
//    }
    /**
     *  getAuthentication으로 리팩토링 필요
     */
    public String getMemberId(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token)
                .getBody().get("memberId", String.class);
    }

    public boolean validateToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token)
                .getBody().getExpiration().before(new Date());
    }

    /**token 생성 algorithm */
//    public String createToken(Authentication authentication){
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));
//        long now = (new Date()).getTime();
//        Date validity = new Date(now + this.expiredMs);
//
//        return Jwts.builder()
//                .setSubject(authentication.getName())
//                .claim(AUTHORITIES_KEY, authorities)
//                .signWith(key, SignatureAlgorithm.HS512)
//                .setExpiration(validity)
//                .compact();
//    }
    /* claim은 payload에 들어갈 정보의 조각 */
    public String createToken(String memId) {
        Claims claims = Jwts.claims();
        claims.put("memId", memId);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiredMs))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }
}
