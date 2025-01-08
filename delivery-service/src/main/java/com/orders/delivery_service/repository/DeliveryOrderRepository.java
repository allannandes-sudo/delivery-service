package com.orders.delivery_service.repository;

import com.orders.delivery_service.entity.DeliveryOrder;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface DeliveryOrderRepository extends ReactiveMongoRepository<DeliveryOrder, String> {

    Mono<DeliveryOrder> findByOrderId(String orderId);

}
