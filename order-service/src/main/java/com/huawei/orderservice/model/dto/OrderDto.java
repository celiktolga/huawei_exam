package com.huawei.orderservice.model.dto;

import com.huawei.orderservice.model.enums.OrderStateEnum;
import com.huawei.orderservice.model.enums.RejectReasonEnum;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private int id;
    private int customerId;
    private OrderStateEnum state;
    private RejectReasonEnum rejectReason;
    private double amount;

}
