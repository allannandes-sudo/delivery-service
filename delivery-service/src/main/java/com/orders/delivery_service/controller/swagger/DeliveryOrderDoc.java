package com.orders.delivery_service.controller.swagger;

import com.orders.delivery_service.annotations.DefaultSwaggerMessage;
import com.orders.delivery_service.model.DeliveryOrderResponse;
import com.orders.delivery_service.model.PagedDeliveryOrderResponse;
import com.orders.delivery_service.model.Product;
import com.orders.delivery_service.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import reactor.core.publisher.Mono;

public interface DeliveryOrderDoc {

    @DefaultSwaggerMessage
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponse(
            responseCode = "201",
            description = "Create. A requisição foi bem sucedida e um novo recurso foi criado.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DeliveryOrderResponse.class)
            )
    )
    @PostMapping("/{orderId}/customers/{customerId}/product")
    Mono<DeliveryOrderResponse> deliveryOrder(
            @PathVariable String orderId,
            @PathVariable String customerId,
            @RequestBody Product product
    );

    @DefaultSwaggerMessage
    @ResponseStatus(HttpStatus.OK)
    @ApiResponse(
            responseCode = "200",
            description = "Update. A requisição foi bem sucedida e um novo recurso foi atualizado.",
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = DeliveryOrderResponse.class)
            )
    )
    @PatchMapping("/{orderId}/status/{status}")
    Mono<DeliveryOrderResponse> updateOrderStatus(
            @PathVariable String orderId,
            @PathVariable OrderStatus orderStatus
    );

    @DefaultSwaggerMessage
    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    Mono<PagedDeliveryOrderResponse> getDeliveryOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) OrderStatus status

    );
}
