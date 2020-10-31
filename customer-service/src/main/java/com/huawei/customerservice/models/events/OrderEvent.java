package com.huawei.customerservice.models.events;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEvent {
    private int id;
    private int customerId;
    private double amount;
}
