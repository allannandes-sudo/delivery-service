package com.orders.delivery_service.model.error;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Singular;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;


@Getter
@ToString
@Builder
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "ErrorResponse", description = "Retorno em caso de erro nas requisições.")
public class ErrorResponse implements Serializable {

    @Serial
    private static final long serialVersionUID = 5813243523978837146L;

    @Schema(name = "errors", description = "Coleção com os detalhes dos erros ocorridos na requisição.")
    @Singular
    private List<ErrorDetail> errors;

}
