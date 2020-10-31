package com.huawei.orderservice.entity;

import com.huawei.orderservice.model.enums.OrderStateEnum;
import com.huawei.orderservice.model.enums.RejectReasonEnum;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private int customerId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStateEnum state;

    @Enumerated(EnumType.STRING)
    private RejectReasonEnum rejectReason;

    @Column(nullable = false)
    private double amount;


}
