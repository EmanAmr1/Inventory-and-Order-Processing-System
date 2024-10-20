package com.parsing.OrderFulfillmentSystem.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceModel {

    private Long orderId;

    private LocalDateTime invoiceDate;

    private Double total;

    private String paymentStatus;
}
