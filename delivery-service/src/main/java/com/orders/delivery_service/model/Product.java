package com.orders.delivery_service.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = -7734670948656379996L;
    @NotBlank(message = "O campo productId é obrigatório.")
    private String productId;

    @NotBlank(message = "O campo name é obrigatório.")
    @Size(min = 1, max = 100, message = "O nome do produto deve ter entre 1 e 100 caracteres.")
    private String name;

    @NotNull(message = "A quantidade é obrigatório.")
    @Positive(message = "A quantidade deve ser um número positivo.")
    private Integer quantity;

    @NotNull(message = "O price é obrigatório.")
    @Positive(message = "O preço deve ser um número positivo.")
    private double price;
}