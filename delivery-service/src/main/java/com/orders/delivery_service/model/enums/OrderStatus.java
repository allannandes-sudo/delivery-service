package com.orders.delivery_service.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    PENDING("Pending"),
    PROCESSED("Processed"),
    CANCELLED("Cancelled");

    private final String displayName;
}
