package com.example.diary.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Value("${jwt.secret")
    private String secretKey;

    private Long expiredMs = 1000 * 60 * 60l; /* 1시간 */
    
    public String login(String memId, String password) {
        //인증과정 생략
        return JwtUtil.createJwt(memId, secretKey, expiredMs);
    }
}
