package com.huawei.customerservice.models.events;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReserveEvent {
    private Integer orderId;
    private Integer customerId;
    private double amount;
}
