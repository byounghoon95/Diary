package com.example.diary.utils;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class JwtUtil {

    /* before(new Date()) 는 토큰이 만료된게 현재보다 이전이면 토큰은 만료된 것으로 처리 */
    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJwt(token)
                .getBody().getExpiration().before(new Date());
    }

    public static String createJwt(String memId, String secretKey, Long expiredMs) {
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
