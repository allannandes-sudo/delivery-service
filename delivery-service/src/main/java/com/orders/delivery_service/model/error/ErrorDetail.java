package com.orders.delivery_service.model.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetail implements Serializable {

    @Serial
    private static final long serialVersionUID = 4304122220503190876L;

    private String code;

    private String title;

    private String detail;

}