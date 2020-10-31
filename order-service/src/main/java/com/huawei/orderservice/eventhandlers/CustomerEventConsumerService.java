package com.huawei.orderservice.eventhandlers;

import com.huawei.orderservice.model.events.CustomerReservationFailedEvent;
import com.huawei.orderservice.model.events.CustomerReserveEvent;
import com.huawei.orderservice.model.request.ApproveOrderRequest;
import com.huawei.orderservice.model.request.RejectOrderRequest;
import com.huawei.orderservice.service.OrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class CustomerEventConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerEventConsumerService.class);

    private final OrderService orderService;
    private final ModelMapper mapper;

    public CustomerEventConsumerService(OrderService orderService, ModelMapper mapper) {
        this.orderService = orderService;
        this.mapper = mapper;
    }

    @KafkaListener(topics = "CustomerCreditReservedEvent")
    public void customerCreditReservedEvent(CustomerReserveEvent customerReserveEvent) {
        LOGGER.info("received {}", customerReserveEvent);
        orderService.approveOrder(mapper.map(customerReserveEvent, ApproveOrderRequest.class));
    }


    @KafkaListener(topics = "CustomerCreditReservationFailedEvent")
    public void customerCreditReservationFailedEvent(CustomerReservationFailedEvent customerReservationFailedEvent) {
        LOGGER.info("received {}", customerReservationFailedEvent);

        orderService.rejectOrder(mapper.map(customerReservationFailedEvent, RejectOrderRequest.class));

    }

}
