package com.huawei.customerservice.service;

import com.huawei.customerservice.entity.Customer;
import com.huawei.customerservice.eventhandlers.CustomerEventPublisherService;
import com.huawei.customerservice.models.CustomerDto;
import com.huawei.customerservice.models.enums.RejectReasonEnum;
import com.huawei.customerservice.models.events.CustomerReservationFailedEvent;
import com.huawei.customerservice.models.events.CustomerReserveEvent;
import com.huawei.customerservice.models.request.ReserveCreditRequest;
import com.huawei.customerservice.models.request.SaveCustomerRequest;
import com.huawei.customerservice.repository.CustomerRepository;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {

    Logger logger = LoggerFactory.getLogger(CustomerService.class);

    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;
    private final CustomerEventPublisherService customerEventPublisherService;


    public CustomerService(CustomerRepository customerRepository, ModelMapper modelMapper, CustomerEventPublisherService customerEventPublisherService) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
        this.customerEventPublisherService = customerEventPublisherService;
    }

    public CustomerDto saveCustomer(SaveCustomerRequest request) {
        Customer customer = modelMapper.map(request, Customer.class);
        customerRepository.save(customer);
        return modelMapper.map(customer, CustomerDto.class);
    }

    public CustomerDto getCustomerById(Integer id) throws NotFoundException {
        Customer customer = findCustomerEntityById(id);
        return modelMapper.map(customer, CustomerDto.class);
    }

    public void reserveCredit(ReserveCreditRequest request) {

        Customer customer = getCustomer(request);
        if (customer == null) return;

        //check limit
        if (customer.getCreditLimit() <= 0 || request.getAmount() > customer.getCreditLimit()) {

            logger.info("reserveCredit- insufficient credit:  amount:{} customerLimit:{}", request.getAmount(), customer.getCreditLimit());

            customerEventPublisherService.customerCreditReservationFailedEvent(
                    CustomerReservationFailedEvent.builder()
                            .orderId(request.getOrderId())
                            .amount(request.getAmount())
                            .rejectReason(RejectReasonEnum.INSUFFICIENT_CREDIT)
                            .build());

            return;
        }

        //reserving credit
        customer.setCreditLimit(customer.getCreditLimit() - request.getAmount());
        customer.setCreditReservation(customer.getCreditReservation() + request.getAmount());
        customerRepository.save(customer);

        customerEventPublisherService.customerCreditReservedEvent(
                CustomerReserveEvent.builder()
                        .orderId(request.getOrderId())
                        .customerId(request.getCustomerId())
                        .amount(request.getAmount())
                        .build());

    }

    private Customer getCustomer(ReserveCreditRequest request) {
        Optional<Customer> customerOpt = customerRepository.findById(request.getCustomerId());

        if (!customerOpt.isPresent()) {

            logger.info("getCustomer- customer not found Id: {}", request.getCustomerId());
            customerEventPublisherService.customerCreditReservationFailedEvent(
                    CustomerReservationFailedEvent.builder()
                            .orderId(request.getOrderId())
                            .amount(request.getAmount())
                            .rejectReason(RejectReasonEnum.UNKNOWN_CUSTOMER)
                            .build());

            return null;
        }

        return customerOpt.get();
    }


    private Customer findCustomerEntityById(Integer customerId) throws NotFoundException {
        return customerRepository.findById(customerId).orElseThrow(() -> new NotFoundException("Customer Not Found"));
    }

}
