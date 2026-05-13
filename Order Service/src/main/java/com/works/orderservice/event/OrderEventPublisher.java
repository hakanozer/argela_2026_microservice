package com.works.orderservice.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventPublisher {

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private static final String ORDER_CREATED_TOPIC = "order.created";

    public void publishOrderCreated(OrderCreatedEvent event) {

        try {

            byte[] payload = objectMapper.writeValueAsBytes(event);

            CompletableFuture<SendResult<String, byte[]>> future =
                    kafkaTemplate.send(
                            ORDER_CREATED_TOPIC,
                            event.customerId().toString(),
                            payload
                    );

            future.whenComplete((result, ex) -> {

                if (ex != null) {

                    log.error(
                            "Kafka mesaj gönderimi BAŞARISIZ. topic={}, orderId={}, error={}",
                            ORDER_CREATED_TOPIC,
                            event.orderId(),
                            ex.getMessage(),
                            ex
                    );

                } else {
                    try {
                        Thread.sleep(3000);
                        log.info(
                                "Kafka mesajı gönderildi. topic={}, partition={}, offset={}, orderId={}",
                                ORDER_CREATED_TOPIC,
                                result.getRecordMetadata().partition(),
                                result.getRecordMetadata().offset(),
                                event.orderId()
                        );
                    }catch (Exception e) { }

                }
            });

        } catch (Exception e) {

            log.error("JSON serialize hatası", e);
        }
    }
}
