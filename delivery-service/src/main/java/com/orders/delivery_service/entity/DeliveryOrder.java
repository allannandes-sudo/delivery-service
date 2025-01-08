package com.orders.delivery_service.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.orders.delivery_service.annotations.ZonedDataTimeFormatter;
import com.orders.delivery_service.model.Product;
import com.orders.delivery_service.model.enums.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Document(collection = "delivery_order")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryOrder implements Serializable {

    @Serial
    private static final long serialVersionUID = -7843199165614395072L;

    @Id
    @JsonIgnore
    private String id;
    @NotNull(message = "O campo orderId é obrigatório.")
    private String orderId;
    @NotNull(message = "O campo customerId é obrigatório.")
    private String customerId;
    @ZonedDataTimeFormatter
    private LocalDateTime createDateTime;
    @ZonedDataTimeFormatter
    private LocalDateTime sendDateTime;
    @NotEmpty(message = "A lista de produtos não pode estar vazia.")
    private List<Product> products;
    private OrderStatus status;
}
