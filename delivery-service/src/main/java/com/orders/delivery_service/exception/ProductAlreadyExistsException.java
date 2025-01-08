package com.orders.delivery_service.exception;

import com.orders.delivery_service.model.Product;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class ProductAlreadyExistsException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -5764633793739451463L;

    public ProductAlreadyExistsException(Product product) {
        super(String.format("Produto '%s' com ID '%s' já existe com os mesmos atributos.",
                product.getName(), product.getProductId()));
    }
}
