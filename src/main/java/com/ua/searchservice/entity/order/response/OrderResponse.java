package com.ua.searchservice.entity.order.response;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class OrderResponse {

    private Long id;

    private String description;

    private Long customerId;

    private OffsetDateTime createdAt;

    private Long specialistId;
}