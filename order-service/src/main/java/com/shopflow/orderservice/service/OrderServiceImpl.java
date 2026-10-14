package com.shopflow.orderservice.service;

import com.shopflow.orderservice.client.InventoryClient;
import com.shopflow.orderservice.client.ProductClient;
import com.shopflow.orderservice.client.UserClient;
import com.shopflow.orderservice.dto.*;
import com.shopflow.orderservice.entity.Order;
import com.shopflow.orderservice.entity.OrderItem;
import com.shopflow.orderservice.entity.OrderStatus;
import com.shopflow.orderservice.event.OrderCreatedEvent;
import com.shopflow.orderservice.event.OrderCreatedItem;
import com.shopflow.orderservice.kafka.OrderEventProducer;
import com.shopflow.orderservice.repository.OrderItemRepository;
import com.shopflow.orderservice.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final UserClient userClient;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderEventProducer orderEventProducer;

    @Override
    @CircuitBreaker(
            name = "userService",
            fallbackMethod = "getUserByIdFallback"
    )
    public UserResponse getUserById(Long id) {
        return userClient.getUserById(id);
    }

    private UserResponse getUserByIdFallback(Long id, Throwable ex) {
        throw new IllegalStateException(
                "User Service is currently unavailable. Please try again later.",
                ex
        );
    }

    @Override
    @CircuitBreaker(
            name = "productService",
            fallbackMethod = "getProductByIdFallback"
    )
    public ProductResponse getProductById(Long id) {
        return productClient.getProductById(id);
    }

    private ProductResponse getProductByIdFallback(Long id, Throwable ex) {
        throw new IllegalStateException(
                "Product Service is currently unavailable. Please try again later.",
                ex
        );
    }

    @Override
    @CircuitBreaker(
            name = "inventoryService",
            fallbackMethod = "isProductAvailableFallback"
    )
    public Boolean isProductAvailable(Long productId, Integer quantity) {
        return inventoryClient.isProductAvailable(productId, quantity);
    }

    private Boolean isProductAvailableFallback(
            Long productId,
            Integer quantity,
            Throwable ex
    ) {
        throw new IllegalStateException(
                "Inventory Service is currently unavailable. Please try again later.",
                ex
        );
    }

    @Override
    public OrderResponse createOrder(Long userId, CreateOrderRequest request) {

        UserResponse user = getUserById(userId);
        List<OrderItemResponse> itemResponses = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItemRequest item : request.items()) {

            ProductResponse product =
                    getProductById(item.productId());

            Boolean available = isProductAvailable(
                    item.productId(),
                    item.quantity()
            );

            if (!available) {
                throw new RuntimeException(
                        "Product " + item.productId() + " is not available"
                );
            }

            BigDecimal itemTotal =
                    product.price().multiply(
                            BigDecimal.valueOf(item.quantity())
                    );

            totalAmount = totalAmount.add(itemTotal);

            itemResponses.add(
                    new OrderItemResponse(
                            item.productId(),
                            item.quantity(),
                            product.price()
                    )
            );
        }

        Order order = new Order();

        order.setUserId(userId);
        order.setStatus(OrderStatus.CREATED);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        for (OrderItemRequest item : request.items()) {

            ProductResponse product =
                    getProductById(item.productId());

            OrderItem orderItem = new OrderItem();

            orderItem.setOrderId(savedOrder.getId());
            orderItem.setProductId(item.productId());
            orderItem.setQuantity(item.quantity());
            orderItem.setPrice(product.price());

            orderItemRepository.save(orderItem);
        }

        OrderCreatedEvent event = new OrderCreatedEvent(
                savedOrder.getId(),
                userId,
                request.items().stream()
                        .map(item -> new OrderCreatedItem(
                                item.productId(),
                                item.quantity()
                        ))
                        .toList()
        );

        orderEventProducer.publishOrderCreated(event);

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getStatus(),
                savedOrder.getTotalAmount(),
                itemResponses
        );
    }

    @Override
    public OrderResponse getOrderById(Long id) {

        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Order not found")
                );

        List<OrderItem> orderItems =
                orderItemRepository.findByOrderId(order.getId());

        List<OrderItemResponse> itemResponses =
                orderItems.stream()
                        .map(item ->
                                new OrderItemResponse(
                                        item.getProductId(),
                                        item.getQuantity(),
                                        item.getPrice()
                                )
                        )
                        .toList();

        return new OrderResponse(
                order.getId(),
                order.getUserId(),
                order.getStatus(),
                order.getTotalAmount(),
                itemResponses
        );
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {

        List<Order> orders =
                orderRepository.findByUserId(userId);

        return orders.stream()
                .map(order -> {

                    List<OrderItem> orderItems =
                            orderItemRepository.findByOrderId(order.getId());

                    List<OrderItemResponse> itemResponses =
                            orderItems.stream()
                                    .map(item ->
                                            new OrderItemResponse(
                                                    item.getProductId(),
                                                    item.getQuantity(),
                                                    item.getPrice()
                                            )
                                    )
                                    .toList();

                    return new OrderResponse(
                            order.getId(),
                            order.getUserId(),
                            order.getStatus(),
                            order.getTotalAmount(),
                            itemResponses
                    );
                })
                .toList();
    }
}