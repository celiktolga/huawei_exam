package com.huawei.customerservice.eventhandlers;

import com.huawei.customerservice.models.events.CustomerReservationFailedEvent;
import com.huawei.customerservice.models.events.CustomerReserveEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class CustomerEventPublisherService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerEventPublisherService.class);

    private final KafkaTemplate<String, ?> kafkaTemplate;

    public CustomerEventPublisherService(KafkaTemplate<String, ?> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void customerCreditReservedEvent(CustomerReserveEvent customerReserveEvent) {
        String topic = "CustomerCreditReservedEvent";
        LOGGER.info("sending payload='{}' to topic='{}'", customerReserveEvent, topic);
        kafkaTemplate.send(MessageBuilder.withPayload(customerReserveEvent).setHeader(KafkaHeaders.TOPIC, topic).build());
    }

    public void customerCreditReservationFailedEvent(CustomerReservationFailedEvent customerReservationFailedEvent) {
        String topic = "CustomerCreditReservationFailedEvent";
        LOGGER.info("sending payload='{}' to topic='{}'", customerReservationFailedEvent, topic);
        kafkaTemplate.send(MessageBuilder.withPayload(customerReservationFailedEvent).setHeader(KafkaHeaders.TOPIC, topic).build());
    }


}
