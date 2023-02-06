package com.example.diary.member.service;

import com.example.diary.common.CodeEnum;
import com.example.diary.dto.MemberJoinDto;
import com.example.diary.dto.MemberLoginDto;
import com.example.diary.entity.MemberEntity;
import com.example.diary.exception.CustomException;
import com.example.diary.member.repository.MemberRepository;
import com.example.diary.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
    @Value("${jwt.secret")
    private String secretKey;
    private Long expiredMs = 1000 * 60 * 60l; /* 1시간 */
    @Transactional
    public MemberJoinDto join(MemberJoinDto dto) {
        //memId 중복 체크
        memberRepository.findByMemId(dto.getMemId())
                .ifPresent(member -> {
                    throw new CustomException(CodeEnum.DUPLICATED_MEMBER, dto.getMemId() + " 는 이미 등록된 아이디입니다.");
                });
        dto.setPassword(encoder.encode(dto.getPassword()));
        memberRepository.save(dto.toEntity());
        return dto;

    }
    public String login(MemberLoginDto dto) {
        return JwtUtil.createJwt(dto.getMemId(), secretKey, expiredMs);
    }
    public String exception() {
        return "error";
    }


}
