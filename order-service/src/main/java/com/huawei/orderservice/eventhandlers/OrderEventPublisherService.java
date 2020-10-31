package com.huawei.orderservice.eventhandlers;

import com.huawei.orderservice.model.events.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderEventPublisherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventPublisherService.class);

    private final KafkaTemplate<String, ?> kafkaTemplate;

    public OrderEventPublisherService(KafkaTemplate<String, ?> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void orderCreatedEvent(OrderEvent payload) {
        String topic = "OrderCreatedEvent";
        LOGGER.info("sending payload='{}' to topic='{}'", payload, topic);
        kafkaTemplate.send(MessageBuilder.withPayload(payload).setHeader(KafkaHeaders.TOPIC, topic).build());
    }
}
