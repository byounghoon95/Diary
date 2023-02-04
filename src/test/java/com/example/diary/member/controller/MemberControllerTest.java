package com.example.diary.member.controller;

import com.example.diary.common.CodeEnum;
import com.example.diary.dto.MemberJoinDto;
import com.example.diary.exception.CustomException;
import com.example.diary.member.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    MemberService memberService;
    @Autowired
    ObjectMapper objectMapper;

    //http request에 보낼 땐 byte로 전송
    @DisplayName(value = "회원가입 성공")
    @Test
    void join() throws Exception {
        String memId = "bhlee";
        String password = "hoon1";
        String name = "이병훈";

        mockMvc.perform(post("/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberJoinDto(memId, password, name))))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName(value = "회원가입 실패 - memId 중복")
    @Test
    void join_fail() throws Exception {
        String memId = "bhlee";
        String password = "hoon1";
        String name = "이병훈";

        when(memberService.join(any()))
                .thenThrow(new CustomException(CodeEnum.MEMBER_DUPLICATED.getCode(), memId + " 는 이미 등록된 아이디입니다."));

        mockMvc.perform(post("/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(new MemberJoinDto(memId, password, name))))
                .andDo(print())
                .andExpect(status().isConflict());
    }

}