package com.huawei.orderservice.model.events;

import com.huawei.orderservice.model.enums.RejectReasonEnum;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerReservationFailedEvent {
    private Integer orderId;
    private double amount;
    private RejectReasonEnum rejectReason;
}
