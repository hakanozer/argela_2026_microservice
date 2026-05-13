package com.works.paymentservice;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.kafka.clients.consumer.ConsumerRecord;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final ObjectMapper objectMapper;

    @KafkaListener(
            topics = "order.created",
            groupId = "payment-service",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void handleOrderCreated(
            ConsumerRecord<String, byte[]> record,
            Acknowledgment acknowledgment) {

        try {
            byte[] bytes = record.value();
            String json = new String(bytes);
            OrderCreatedEvent event = objectMapper.readValue(json, OrderCreatedEvent.class);
            Thread.sleep(3000);
            log.info(
                    "Sipariş eventi alındı. orderId={}, customerId={}, amount={}",
                    event.orderId(),
                    event.customerId(),
                    event.totalAmount()
            );
            acknowledgment.acknowledge();
        } catch (Exception e) {
            log.error("Kafka mesaj parse hatası", e);
        }
    }
}