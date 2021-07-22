package com.ua.searchservice.entity.order.response;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CreateOrderResponse {

    private String description;

    private OffsetDateTime createdAt;

    private Long customerId;

    public CreateOrderResponse(String description, OffsetDateTime createdAt, Long customerId) {
        this.description = description;
        this.createdAt = createdAt;
        this.customerId = customerId;
    }
}