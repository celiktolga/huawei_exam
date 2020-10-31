package com.huawei.customerservice.models.events;

import com.huawei.customerservice.models.enums.RejectReasonEnum;
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
