package com.orders.delivery_service.kafka.producer.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.orders.delivery_service.kafka.producer.KafkaEventProcess;
import com.orders.delivery_service.kafka.producer.KafkaEventProducer;
import com.orders.delivery_service.model.DeliveryOrderResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaEventProcessImpl extends KafkaEventProducer<DeliveryOrderResponse> implements KafkaEventProcess {

    public KafkaEventProcessImpl(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        super(objectMapper, kafkaTemplate, "order-service");
    }

    @Override
    public void sendStatus(String kafkaTopic, DeliveryOrderResponse payload) {
        log.info("Sending kafka topic KafkaEventProcessImpl: {}, DeliveryOrderResponse: {}", kafkaTopic, payload);
        sendKafka(kafkaTopic, payload);
    }
}