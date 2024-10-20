package com.parsing.OrderFulfillmentSystem.Model;


import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
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
