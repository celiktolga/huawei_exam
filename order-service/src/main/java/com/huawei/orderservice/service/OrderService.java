package com.huawei.orderservice.service;

import com.huawei.orderservice.entity.Order;
import com.huawei.orderservice.eventhandlers.OrderEventPublisherService;
import com.huawei.orderservice.model.dto.OrderDto;
import com.huawei.orderservice.model.enums.OrderStateEnum;
import com.huawei.orderservice.model.enums.RejectReasonEnum;
import com.huawei.orderservice.model.events.OrderEvent;
import com.huawei.orderservice.model.request.ApproveOrderRequest;
import com.huawei.orderservice.model.request.RejectOrderRequest;
import com.huawei.orderservice.model.request.SaveOrderRequest;
import com.huawei.orderservice.repository.OrderRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RefreshScope
public class OrderService {

    Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final ModelMapper mapper;
    private final OrderEventPublisherService orderEventPublisherService;

    public OrderService(OrderRepository orderRepository, ModelMapper mapper, OrderEventPublisherService orderEventPublisherService) {
        this.orderRepository = orderRepository;
        this.mapper = mapper;
        this.orderEventPublisherService = orderEventPublisherService;
    }

    public OrderDto saveOrder(SaveOrderRequest request) {
        Order order = Order.builder().customerId(request.getCustomerId()).state(OrderStateEnum.PENDING).amount(request.getAmount()).build();
        orderRepository.save(order);
        orderEventPublisherService.orderCreatedEvent(mapper.map(order, OrderEvent.class));
        return mapper.map(order, OrderDto.class);
    }

    public void approveOrder(ApproveOrderRequest request) {
        orderRepository.findById(request.getOrderId()).ifPresent(order -> {
            order.setState(OrderStateEnum.APPROVED);
            orderRepository.save(order);
        });

        logger.info("approveOrder- order not found orderId:{}", request.getOrderId());
    }

    public void rejectOrder(RejectOrderRequest request) {
        orderRepository.findById(request.getOrderId()).ifPresent(order -> {
            order.setState(OrderStateEnum.REJECTED);
            order.setRejectReason(RejectReasonEnum.valueOf(request.getRejectReason().name()));
            orderRepository.save(order);
        });
        logger.info("rejectOrder- order not found orderId:{}", request.getOrderId());
    }

    public OrderDto getOrderById(Integer id) throws NotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order Not Found"));

        return mapper.map(order, OrderDto.class);
    }

    public List<OrderDto> getOrderByCustomerId(Integer customerId) {
        List<Order> orderList = orderRepository.findByCustomerId(customerId);
        return orderList.stream().map(order -> mapper.map(order, OrderDto.class)).collect(Collectors.toList());
    }
}
