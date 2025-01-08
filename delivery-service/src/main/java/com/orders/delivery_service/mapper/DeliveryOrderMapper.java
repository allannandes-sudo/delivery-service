package com.orders.delivery_service.mapper;

import com.orders.delivery_service.entity.DeliveryOrder;
import com.orders.delivery_service.model.DeliveryOrderResponse;
import com.orders.delivery_service.model.Product;
import com.orders.delivery_service.model.enums.OrderStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.time.LocalDateTime;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface DeliveryOrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "sendDateTime", ignore = true)
    @Mapping(target = "createDateTime", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "status", expression = "java(pendingStatus())")
    @Mapping(target = "products", expression = "java(new java.util.ArrayList<>(java.util.Collections.singletonList(product)))")
    DeliveryOrder toEntity(String orderId, String customerId, Product product);

    DeliveryOrderResponse toDeliveryOrderResponse(DeliveryOrder deliveryOrder);

    @Mapping(target = "sendDateTime", source = "processDateTime")
    @Mapping(target = "status", source = "orderStatus")
    void updateDeliveryOrder(LocalDateTime processDateTime, OrderStatus orderStatus, @MappingTarget DeliveryOrder existingdeliveryOrder);

    default OrderStatus pendingStatus() {
        return OrderStatus.PENDING;
    }
}
