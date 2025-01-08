package com.orders.delivery_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 9086096187377717077L;

    public BusinessException(String message) {
        super(message);
    }

}
