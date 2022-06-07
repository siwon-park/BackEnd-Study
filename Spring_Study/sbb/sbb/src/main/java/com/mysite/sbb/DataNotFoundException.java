package com.mysite.sbb;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// 사용자 정의 예외 처리를 위한 DataNotFoundException 클래스
// 404 NOT FOUND일 경우에 reason에 입력한 사유(문자열)가 나옴
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "entity not found")
public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public DataNotFoundException(String msg) {
        super(msg);
    }
}
