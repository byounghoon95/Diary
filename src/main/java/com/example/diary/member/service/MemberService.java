package com.example.diary.member.service;

import com.example.diary.common.CodeEnum;
import com.example.diary.dto.MemberJoinDto;
import com.example.diary.entity.Member;
import com.example.diary.exception.CustomException;
import com.example.diary.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    @Transactional
    public MemberJoinDto join(MemberJoinDto dto) {
        //memId 중복 체크
        memberRepository.findByMemId(dto.getMemId())
                .ifPresent(member -> {
                    throw new CustomException(CodeEnum.MEMBER_DUPLICATED.getCode(), dto.getMemId() + " 는 이미 등록된 아이디입니다.");
                });
        Member member = dto.toEntity();
        memberRepository.save(member);
        return dto;
    }

    public String exception() {
        return "error";
    }
}
