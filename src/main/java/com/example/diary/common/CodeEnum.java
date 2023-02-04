package com.example.diary.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CodeEnum {
    SUCCESS(HttpStatus.OK,"SUCCESS"),
    DUPLICATED_MEMBER(HttpStatus.CONFLICT,"Member id is duplicated"),
    UNKNOWN_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,"UNKNOWN_ERROR");

    private HttpStatus code;
    private String message;

}
