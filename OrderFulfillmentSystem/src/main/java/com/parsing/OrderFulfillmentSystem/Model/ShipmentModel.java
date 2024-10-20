package com.parsing.OrderFulfillmentSystem.Model;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipmentModel {

    private Long orderId;

    private LocalDateTime shipmentDate;

    private String trackingNumber;

    private String carrier;

    private String status;
}
