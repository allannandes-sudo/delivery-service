package com.orders.delivery_service.service;

import com.orders.delivery_service.model.DeliveryOrderResponse;
import com.orders.delivery_service.model.PagedDeliveryOrderResponse;
import com.orders.delivery_service.model.Product;
import com.orders.delivery_service.model.enums.OrderStatus;
import reactor.core.publisher.Mono;

public interface DeliveryOrderService {

    Mono<DeliveryOrderResponse> addOrUpdateProduct(
            String orderId,
            String customerId,
            Product product
    );

    Mono<DeliveryOrderResponse> updateStatus(
            String orderId,
            OrderStatus orderStatus
    );

    Mono<PagedDeliveryOrderResponse> getDeliveryOrders(int page, int size, String customerId, OrderStatus status);
}
