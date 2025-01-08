package com.orders.delivery_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CustomerMismatchException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 6562179824388758689L;

    public CustomerMismatchException(String existingCustomerId, String providedCustomerId) {
        super(String.format("Incompatibilidade de ID do cliente: esperado '%s' mas consegui '%s'.",
                existingCustomerId, providedCustomerId)
        );
    }
}
