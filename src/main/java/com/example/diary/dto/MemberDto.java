package com.example.diary.dto;

import com.example.diary.entity.Member;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberDto {
    private final String memId;
    private final String password;
    private final String name;

    public Member toEntity() {
        return Member.builder()
                .memId(memId)
                .name(password)
                .password(name)
                .build();
    }
}
