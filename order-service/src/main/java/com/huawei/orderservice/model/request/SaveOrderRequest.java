package com.huawei.orderservice.model.request;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveOrderRequest {
    private int customerId;
    private double amount;
}
