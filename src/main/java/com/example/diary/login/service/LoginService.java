package com.example.diary.login.service;

import com.example.diary.dto.MemberDto;
import com.example.diary.entity.Member;
import com.example.diary.login.repository.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class LoginService {
    private final LoginRepository loginRepository;
    @Transactional
    public void join(MemberDto dto) {
        Member member = dto.toEntity();
        loginRepository.save(member);
    }
}
