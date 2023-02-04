package com.example.diary.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CodeEnum {
    SUCCESS(HttpStatus.OK,"SUCCESS"),
    MEMBER_DUPLICATED(HttpStatus.CONFLICT,""),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"UNKNOWN_ERROR");

    private HttpStatus code;
    private String message;

}
