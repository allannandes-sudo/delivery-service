package com.orders.delivery_service.kafka.producer;

import com.orders.delivery_service.model.DeliveryOrderResponse;

public interface KafkaEventProcess {
    void sendStatus(String kafkaTopic, DeliveryOrderResponse payload);
}
