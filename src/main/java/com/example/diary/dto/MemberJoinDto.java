package com.example.diary.dto;

import com.example.diary.entity.MemberEntity;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MemberJoinDto {
    private final String memId;
    private final String password;
    private final String name;

    public MemberEntity toEntity() {
        return MemberEntity.builder()
                .memId(memId)
                .name(password)
                .password(name)
                .build();
    }
}
