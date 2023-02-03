package com.example.diary.exceptionhandler;

import com.example.diary.common.CodeEnum;
import com.example.diary.exception.CustomException;
import com.example.diary.response.CommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 컨트롤러에서 throw new CustomException(CodeEnum.UNKOWN_ERROR)으로
 * 에러를 던지면 CustomException으로 실행된 에러가 RestControllerAdvice를 타고
 * handlerCustomException으로 넘어와 아래 로직을 실행 후 결과값을 반환하게 된다
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomException.class)
    public CommonResponse handlerCustomException(CustomException e) {
        return CommonResponse.builder()
                .returnCode(e.getReturnCode())
                .returnMessage(e.getReturnMessage())
                .build();
    }
    /**
     * 예외를 아무리 잘 처리해도 예상치 못한 예외가 발생할 수 있다.
     * 그런 경우 예외 처리를 위해 꼭 필요하다.
     */
    @ExceptionHandler(Exception.class)
    public CommonResponse handlerException(Exception e) {
        return CommonResponse.builder()
                .returnCode(CodeEnum.UNKOWN_ERROR.getCode())
                .returnMessage(CodeEnum.UNKOWN_ERROR.getMessage())
                .build();
    }
}
