package com.parsing.OrderFulfillmentSystem.Model;


import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {

    private String name;

    private String description;

    private Double price;

    private Integer stock;

}
