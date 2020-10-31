package com.huawei.orderservice.model.request;


import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApproveOrderRequest {
    private Integer orderId;
    private Integer customerId;
}
