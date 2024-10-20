package com.parsing.OrderFulfillmentSystem.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
