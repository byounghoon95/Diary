package com.example.diary.member.controller;

import com.example.diary.common.CodeEnum;
import com.example.diary.dto.MemberJoinDto;
import com.example.diary.dto.MemberLoginDto;
import com.example.diary.exception.CustomException;
import com.example.diary.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.apache.bcel.classfile.Code;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//Security가 들어가면 with(csrf()) 를 사용해야 에러가 나지 않음
@WebMvcTest
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    MemberService memberService;
    @Autowired
    ObjectMapper objectMapper;

    //http request에 보낼 땐 byte로 전송
    @Test
    @WithMockUser
    @DisplayName(value = "회원가입 성공")
    void join() throws Exception {
        String memId = "bhlee";
        String password = "hoon1";
        String name = "이병훈";

        mockMvc.perform(post("/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberJoinDto(memId, password, name))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    @DisplayName(value = "회원가입 실패 - memId 중복")
    void join_fail() throws Exception {
        String memId = "bhlee";
        String password = "hoon1";
        String name = "이병훈";

        when(memberService.join(any()))
                .thenThrow(new CustomException(CodeEnum.DUPLICATED_MEMBER, memId + " 는 이미 등록된 아이디입니다."));

        mockMvc.perform(post("/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberJoinDto(memId, password, name))))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName(value = "로그인 성공")
    void login_success() throws Exception{
        String memId = "bhlee";
        String password = "hoon1";

        when(memberService.login(any()))
                .thenReturn("token");

        mockMvc.perform(post("/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberLoginDto(memId, password))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithAnonymousUser
    @DisplayName(value = "로그인 실패 - ID가 일치하지 않음")
    void login_fail_id_notFound() throws Exception{
        String memId = "bhlee";
        String password = "hoon1";

        when(memberService.login(any()))
                .thenThrow(new CustomException(CodeEnum.NOT_FOUND_MEMBER,"아이디가 일치하지 않음"));

        mockMvc.perform(post("/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberLoginDto(memId, password))))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName(value = "로그인 실패 - PW가 일치하지 않음")
    void login_fail_pw_wrong() throws Exception{
        String memId = "bhlee";
        String password = "hoon1";

        when(memberService.login(any()))
                .thenThrow(new CustomException(CodeEnum.INVALID_PASSWORD,"아이디가 일치하지 않음"));

        mockMvc.perform(post("/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberLoginDto(memId, password))))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}