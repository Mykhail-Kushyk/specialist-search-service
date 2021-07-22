package com.ua.searchservice.entity.order.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreateOrderRequest {

    @NotBlank
    private String description;
}