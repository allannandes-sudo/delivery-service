package com.orders.delivery_service.service;

import com.orders.delivery_service.model.DeliveryOrderResponse;

public interface ProcessEvent {
    void sendProcessEvent(DeliveryOrderResponse orderResponse);
}
