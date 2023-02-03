package com.example.diary.login.controller;

import com.example.diary.dto.MemberDto;
import com.example.diary.login.service.LoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"로그인 및 회원가입 API"})
@RequiredArgsConstructor
@Getter
@RestController
public class LoginController {
    private final LoginService loginService;
    @ApiOperation(value = "회원가입", notes = "회원의 정보를 입력받아 등록한다")
    @PostMapping("/join")
    public void join(MemberDto dto) {
        loginService.join(dto);

    }
}
