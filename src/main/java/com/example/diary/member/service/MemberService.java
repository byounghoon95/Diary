package com.example.diary.member.service;

import com.example.diary.common.CodeEnum;
import com.example.diary.dto.MemberJoinDto;
import com.example.diary.entity.MemberEntity;
import com.example.diary.exception.CustomException;
import com.example.diary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder encoder;
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

    public String exception() {
        return "error";
    }
}
