package com.example.diary.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeEnum {
    SUCCESS("0000","SUCCESS"),
    UNKOWN_ERROR("9999","UNKNOWN_ERROR");

    private String code;
    private String message;

}
