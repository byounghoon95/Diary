package com.example.diary.member.controller;

import com.example.diary.common.CodeEnum;
import com.example.diary.dto.MemberJoinDto;
import com.example.diary.dto.MemberLoginDto;
import com.example.diary.member.service.MemberService;
import com.example.diary.response.CommonResponse;
import com.example.diary.utils.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"로그인 및 회원가입 API"})
@RequiredArgsConstructor
@Getter
@RequestMapping("/users")
@RestController
public class MemberController {
    private final MemberService memberService;
    private final UserService userService;

    @ApiOperation(value = "회원가입", notes = "회원의 정보를 입력받아 등록한다")
    @PostMapping("/join")
    public CommonResponse join(@RequestBody MemberJoinDto dto) {
        return new CommonResponse(CodeEnum.SUCCESS, memberService.join(dto));
    }

    @ApiOperation(value = "로그인", notes = "회원의 정보로 로그인하면 JWT를 발급한다")
    @PostMapping("/login")
    public CommonResponse login(@RequestBody MemberLoginDto dto) {
        String token = userService.login("","");
        return new CommonResponse(CodeEnum.SUCCESS, token);
    }

//    @GetMapping("/exception")
//    public String exception() {
//        if (true) {
//            throw new CustomException(CodeEnum.UNKNOWN_ERROR);
//        }
//        return memberService.exception();
//    }
}
