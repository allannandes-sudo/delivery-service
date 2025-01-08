package com.orders.delivery_service.kafka.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.concurrent.CompletableFuture;


@Slf4j
@RequiredArgsConstructor
public abstract class KafkaEventProducer<E> {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String destinyName;

    protected void sendKafka(String kafkaTopicName, E payload) {
        try {
            log.info("Iniciando o envio para o {} o payload: {} , no topico: {}", destinyName, payload, kafkaTopicName);
            var future = kafkaTemplate.send(kafkaTopicName, objectMapper.writeValueAsString(payload));
            callBackFuture(future);
        } catch (Exception ex) {
            log.error("Ocorreu um erro ao enviar para o {} o payload: {} , no topico: {}", destinyName, payload, kafkaTopicName);
            log.error(ex.getMessage());
        }
    }


    private void callBackFuture(CompletableFuture<SendResult<String, String>> future) {
        future.whenComplete((message, ex) -> {
            if (ex == null) {
                var topicName = message.getProducerRecord().topic();
                log.info("Enviado com sucesso ao {} o payload: {}, e topico: {}", destinyName, message, topicName);
            } else {
                log.error("Ocorreu um erro ao enviar payload ao {}", destinyName, ex);
            }
        });
    }
}