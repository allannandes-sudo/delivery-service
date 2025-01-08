package com.orders.delivery_service.exception;

import com.orders.delivery_service.model.error.ErrorDetail;
import com.orders.delivery_service.model.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Collections;


@Component
@Order(-2)
@Slf4j
public class GlobalErrorAttributes extends AbstractErrorWebExceptionHandler {

    public GlobalErrorAttributes(ErrorAttributes errorAttributes,
                                 WebProperties.Resources resourceProperties,
                                 ApplicationContext applicationContext,
                                 ServerCodecConfigurer codecConfigurer) {
        super(errorAttributes, resourceProperties, applicationContext);
        this.setMessageWriters(codecConfigurer.getWriters());
    }


    @Override
    protected RouterFunction<ServerResponse> getRoutingFunction(ErrorAttributes errorAttributes) {
        return RouterFunctions.route(RequestPredicates.all(), this::formatErrorResponse);
    }

    private Mono<ServerResponse> formatErrorResponse(ServerRequest request) {
        Throwable throwable = getError(request);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        String message = throwable.getMessage() != null ? throwable.getMessage() : "An unexpected error occurred";

        ResponseStatus responseStatus = throwable.getClass().getAnnotation(ResponseStatus.class);
        if (responseStatus != null) {
            status = responseStatus.code();
        }

        var result = ServerResponse
                .status(status.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(buildErrorResponse(
                        String.valueOf(status.value()),
                        status.name(),
                        message
                )));
        log.info("Response erro global: {}", result);
        return result;
    }

    private ErrorResponse buildErrorResponse(String statusCode, String exceptionMessage, String error) {
        return ErrorResponse.builder()
                .errors(Collections.singletonList(ErrorDetail.builder()
                        .code(statusCode)
                        .title(exceptionMessage)
                        .detail(error)
                        .build()))
                .build();
    }
}