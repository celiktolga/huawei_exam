package com.huawei.orderservice.model.request;


import com.huawei.orderservice.model.enums.RejectReasonEnum;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RejectOrderRequest {
    private Integer orderId;
    private RejectReasonEnum rejectReason;
}
