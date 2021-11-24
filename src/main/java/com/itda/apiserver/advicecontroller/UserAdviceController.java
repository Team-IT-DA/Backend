package com.itda.apiserver.advicecontroller;

import com.itda.apiserver.dto.ApiResult;
import com.itda.apiserver.exception.EmailDuplicationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserAdviceController {

    @ExceptionHandler(EmailDuplicationException.class)
    public String handleEmailDuplicationException() {
        return "이메일이 중복되었습니다.";
    }

}
