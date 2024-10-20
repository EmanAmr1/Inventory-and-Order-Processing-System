package com.parsing.OrderFulfillmentSystem.Model;


import lombok.*;

import java.time.LocalDateTime;
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceModel {

    private Long orderId;

    private LocalDateTime invoiceDate;

    private Double total;

    private String paymentStatus;
}
