package com.example.usersservice.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FeignErrorDecoder implements ErrorDecoder {

    @Override
    public Exception decode(String methodKey, Response response) {
        switch (response.status()) {
            case 400: // 400번일 경우
                break;
            case 404: // 404번일 경우
                if (methodKey.contains("getOrders")) { // 메서드 이름에 getOrders가 있는데 예외가 발생할 경우
                    return new ResponseStatusException(HttpStatus.valueOf(response.status()),
                            "User's order is empty"); // http 상태 코드와 메세지를 함께 반환
                }
                break;
            default:
                return new Exception(response.reason()); // 예외 발생 이유를 반환
        }
        return null;
    }
}

