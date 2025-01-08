package com.orders.delivery_service.annotations;

import com.orders.delivery_service.model.error.ErrorResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)

@ApiResponse(
        responseCode = "400",
        description = "Bad Request. Os dados da requisição estão inválidos.",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)
        )
)
@ApiResponse(
        responseCode = "401",
        description = "Unauthorized. A solicitação não foi aplicada porque não possui credenciais " +
                "de autenticação válidas para o recurso de destino.",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)
        )
)
@ApiResponse(
        responseCode = "403",
        description = "Forbidden. Não autorizado, a solicitação foi recusando.",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)
        )
)
@ApiResponse(
        responseCode = "404",
        description = "Not Found. O servidor não pode encontrar o recurso solicitado.",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)
        )
)
@ApiResponse(
        responseCode = "405",
        description = "Method Not Allowed.",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)
        )
)
@ApiResponse(
        responseCode = "408",
        description = "Request Timeout.",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)
        )
)
@ApiResponse(
        responseCode = "422",
        description = "Business Exception. Erro de negócio.",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)
        )
)
@ApiResponse(
        responseCode = "500",
        description = "Internal Server Error. O servidor encontrou uma condição inesperada que o impediu " +
                "de atender à solicitação.",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)
        )
)
@ApiResponse(
        responseCode = "502",
        description = "Bad Gateway",
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ErrorResponse.class)
        )
)
public @interface DefaultSwaggerMessage {

}
