package com.parsing.OrderFulfillmentSystem.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderModel {

    private String customerName;

    private Long productId;

    private Integer quantity;

    private LocalDateTime orderDate;

    private String status;
}
