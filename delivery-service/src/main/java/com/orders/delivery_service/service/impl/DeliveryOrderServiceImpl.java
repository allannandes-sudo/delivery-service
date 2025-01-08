package com.orders.delivery_service.service.impl;

import com.orders.delivery_service.entity.DeliveryOrder;
import com.orders.delivery_service.exception.BusinessException;
import com.orders.delivery_service.exception.CustomerMismatchException;
import com.orders.delivery_service.exception.ProductAlreadyExistsException;
import com.orders.delivery_service.mapper.DeliveryOrderMapper;
import com.orders.delivery_service.mapper.ProductMapper;
import com.orders.delivery_service.model.DeliveryOrderResponse;
import com.orders.delivery_service.model.PagedDeliveryOrderResponse;
import com.orders.delivery_service.model.Product;
import com.orders.delivery_service.model.enums.OrderStatus;
import com.orders.delivery_service.repository.DeliveryOrderRepository;
import com.orders.delivery_service.service.DeliveryOrderService;
import com.orders.delivery_service.service.ProcessEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.orders.delivery_service.model.enums.OrderStatus.PENDING;
import static com.orders.delivery_service.model.enums.OrderStatus.PROCESSED;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryOrderServiceImpl implements DeliveryOrderService {

    private static final String MESSAGE = "O status do pedido não está como 'Pendente'," +
            " portanto, não é possível realizar alterações.";
    private final DeliveryOrderRepository deliveryOrderRepository;
    private final DeliveryOrderMapper deliveryOrderMapper;
    private final ProductMapper productMapper;
    private final ProcessEvent processEvent;

    public Mono<DeliveryOrderResponse> addOrUpdateProduct(
            String orderId,
            String customerId,
            Product product
    ) {
        return deliveryOrderRepository.findByOrderId(orderId)
                .flatMap(order -> {
                    if (!order.getStatus().equals(PENDING)) {
                        return Mono.error(new BusinessException(MESSAGE));
                    } else {
                        if (!order.getCustomerId().equals(customerId))
                            return Mono.error(new CustomerMismatchException(order.getCustomerId(), customerId));
                        return updateOrderWithProduct(order, product);
                    }
                })
                .switchIfEmpty(createNewOrder(orderId, customerId, product))
                .flatMap(savedOrder -> Mono.justOrEmpty(deliveryOrderMapper.toDeliveryOrderResponse(savedOrder)));
    }

    @Override
    public Mono<DeliveryOrderResponse> updateStatus(String orderId, OrderStatus orderStatus) {
        if (orderStatus == PENDING) {
            return Mono.error(new IllegalArgumentException("O status fornecido não é permitido."));
        }

        return deliveryOrderRepository.findByOrderId(orderId)
                .switchIfEmpty(Mono.error(new BusinessException("Pedido não encontrado.")))
                .flatMap(order -> {
                    if (order.getStatus() == PROCESSED || order.getStatus() == OrderStatus.CANCELLED) {
                        return Mono.error(new BusinessException("O pedido já está processado ou cancelado."));
                    }
                    deliveryOrderMapper.updateDeliveryOrder(LocalDateTime.now(), orderStatus, order);
                    return deliveryOrderRepository.save(order)
                            .flatMap(savedOrder -> {
                                DeliveryOrderResponse orderResponse = deliveryOrderMapper
                                        .toDeliveryOrderResponse(savedOrder);
                                processEvent.sendProcessEvent(orderResponse);
                                return Mono.justOrEmpty(orderResponse);
                            });
                });
    }

    public Mono<PagedDeliveryOrderResponse> getDeliveryOrders(int page, int size, String customerId, OrderStatus status) {
        return deliveryOrderRepository.findAll(Sort.by("createDateTime").ascending())
                .filter(order -> customerId == null || order.getCustomerId().equals(customerId))
                .filter(order -> status == null || order.getStatus().equals(status))
                .collectList()
                .flatMap(filteredOrders -> {
                    int totalSize = filteredOrders.size();
                    log.info("Total size: {}", totalSize);
                    log.info("page: {}, size: {}", page, size);

                    int adjustedPage = page > 0 ? page - 1 : 0;

                    return Mono.just(filteredOrders)
                            .flatMapMany(orders -> Mono.justOrEmpty(orders.stream()
                                    .skip((long) adjustedPage * size)
                                    .limit(size)
                                    .map(deliveryOrderMapper::toDeliveryOrderResponse)
                                    .toList()))
                            .collectList()
                            .map(deliveryOrders -> PagedDeliveryOrderResponse.builder()
                                    .page(page)
                                    .size(size)
                                    .totalSize(totalSize)
                                    .orders(deliveryOrders.stream().flatMap(List::stream).toList())
                                    .build());
                });
    }

    private Mono<DeliveryOrder> updateOrderWithProduct(DeliveryOrder order, Product product) {
        validateAndUpdateProducts(order, product);
        return deliveryOrderRepository.save(order);
    }

    private Mono<DeliveryOrder> createNewOrder(String orderId, String customerId, Product product) {
        return Mono.defer(() -> {
            log.info("Criando pedido!!!");
            var newOrder = deliveryOrderMapper.toEntity(orderId, customerId, product);
            return deliveryOrderRepository.save(newOrder);
        });
    }

    private void validateAndUpdateProducts(DeliveryOrder order, Product newProduct) {
        log.info("Iniciando validação do produto!!");
        List<Product> products = order.getProducts();
        Optional<Product> existingProductOpt = products.stream()
                .filter(product -> product.getProductId().equals(newProduct.getProductId()))
                .findFirst();
        if (existingProductOpt.isPresent()) {
            Product existingProduct = existingProductOpt.get();
            if (existingProduct.equals(newProduct)) {
                log.error("Produto já cadastrado!!!");
                throw new ProductAlreadyExistsException(newProduct);
            }
            log.info("Atualizando produto!!");
            validateProduct(newProduct);
            productMapper.updateProductFromNewProduct(newProduct, existingProduct);
        } else {
            log.info("Adicionando novo produto!!");
            products.add(newProduct);
        }
    }

    private void validateProduct(Product product) {
        if (product.getName() == null || product.getName().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode ser nulo ou vazio.");
        }
        if (product.getQuantity() == null || product.getQuantity() <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser um número positivo.");
        }
        if (product.getPrice() <= 0) {
            throw new IllegalArgumentException("O preço deve ser um número positivo.");
        }
    }
}
