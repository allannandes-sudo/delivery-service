package com.orders.delivery_service.service.impl;

import com.orders.delivery_service.kafka.producer.KafkaEventProcess;
import com.orders.delivery_service.model.DeliveryOrderResponse;
import com.orders.delivery_service.service.ProcessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProcessEventImpl implements ProcessEvent {

    @Value("${topic.process-event:null}")
    private String topic;
    private final KafkaEventProcess kafkaEventProcess;

    @Override
    public void sendProcessEvent(DeliveryOrderResponse orderResponse) {
        if (topic == null || topic.equals("null") || topic.isEmpty()) {
            log.warn("Topic is not configured properly");
            return;
        }
        log.info("Event sent to Kafka topic '{}' for orderId '{}'", topic, orderResponse.getOrderId());
        kafkaEventProcess.sendStatus(topic, orderResponse);
    }
}
