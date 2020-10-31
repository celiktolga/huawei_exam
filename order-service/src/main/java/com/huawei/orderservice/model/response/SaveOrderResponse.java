package com.huawei.orderservice.model.response;

import com.huawei.orderservice.model.enums.OrderStateEnum;
import com.huawei.orderservice.model.enums.RejectReasonEnum;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaveOrderResponse {
    private int id;
    private int customerId;
    private OrderStateEnum state;
    private RejectReasonEnum rejectReason;
    private double amount;
}
