package com.orders.delivery_service.controller;

import com.orders.delivery_service.controller.swagger.DeliveryOrderDoc;
import com.orders.delivery_service.model.DeliveryOrderResponse;
import com.orders.delivery_service.model.PagedDeliveryOrderResponse;
import com.orders.delivery_service.model.Product;
import com.orders.delivery_service.model.enums.OrderStatus;
import com.orders.delivery_service.service.DeliveryOrderService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequiredArgsConstructor
@Tag(name = "Delivery Order Service", description = "Método para criação e validação de pedidos")
@RequestMapping(path = "/delivery-orders")
public class DeliveryOrderRest implements DeliveryOrderDoc {

    private final DeliveryOrderService deliveryOrderService;

    @PostMapping("/{orderId}/customers/{customerId}/product")
    public Mono<DeliveryOrderResponse> deliveryOrder(
            @PathVariable String orderId,
            @PathVariable String customerId,
            @Valid @RequestBody Product product
    ) {
        return deliveryOrderService.addOrUpdateProduct(orderId, customerId, product)
                .doOnSuccess(responseDeliveryOrder -> log.info("Executado com sucesso: {}", responseDeliveryOrder))
                .doOnError(throwable -> log.info("Erro ao criar ou na validação: {}", throwable.getMessage()));
    }

    @PatchMapping("/{orderId}/status/{status}")
    public Mono<DeliveryOrderResponse> updateOrderStatus(
            @PathVariable String orderId,
            @PathVariable OrderStatus status
    ) {
        return deliveryOrderService.updateStatus(orderId, status)
                .doOnSuccess(responseDeliveryOrder -> log.info("Atualizado com sucesso: {}", responseDeliveryOrder))
                .doOnError(throwable -> log.info("Erro ao processar: {}", throwable.getMessage()));
    }

    @GetMapping
    public Mono<PagedDeliveryOrderResponse> getDeliveryOrders(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String customerId,
            @RequestParam(required = false) OrderStatus status
    ) {
        return deliveryOrderService.getDeliveryOrders(page, size, customerId, status)
                .doOnNext(order -> log.info("Pedido encontrado: {}", order))
                .doOnError(throwable -> log.info("Erro ao buscar pedidos: {}", throwable.getMessage()));
    }
}
