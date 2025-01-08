package com.orders.delivery_service.mapper;

import com.orders.delivery_service.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface ProductMapper {

    void updateProductFromNewProduct(Product newProduct, @MappingTarget Product existingProduct);
}
