package com.huawei.customerservice.eventhandlers;

import com.huawei.customerservice.models.events.OrderEvent;
import com.huawei.customerservice.models.request.ReserveCreditRequest;
import com.huawei.customerservice.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class OrderEventConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEventConsumerService.class);

    private final CustomerService customerService;

    public OrderEventConsumerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    @KafkaListener(topics = "OrderCreatedEvent")
    public void orderCreatedEvent(OrderEvent orderDto) {
        LOGGER.info("received OrderCreatedEvent {}", orderDto);
        customerService.reserveCredit(ReserveCreditRequest.builder().orderId(orderDto.getId()).customerId(orderDto.getCustomerId()).amount(orderDto.getAmount()).build());
    }
}
