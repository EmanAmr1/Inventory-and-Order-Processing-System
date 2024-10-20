package com.parsing.OrderFulfillmentSystem.Model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductModel {

    private String name;

    private String description;

    private Double price;

    private Integer stock;

}
