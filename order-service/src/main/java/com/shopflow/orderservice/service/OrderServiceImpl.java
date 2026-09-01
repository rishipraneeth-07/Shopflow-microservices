package com.shopflow.orderservice.service;

import com.shopflow.orderservice.client.InventoryClient;
import com.shopflow.orderservice.client.ProductClient;
import com.shopflow.orderservice.client.UserClient;
import com.shopflow.orderservice.dto.*;
import com.shopflow.orderservice.entity.Order;
import com.shopflow.orderservice.entity.OrderItem;
import com.shopflow.orderservice.entity.OrderStatus;
import com.shopflow.orderservice.repository.OrderItemRepository;
import com.shopflow.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl  implements OrderService {
    private final UserClient userClient;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Override
    public UserResponse getUserById(Long id) {
        return userClient.getUserById(id);
    }

    @Override
    public ProductResponse getProductById(Long id) {
        return productClient.getProductById(id);
    }

    @Override
    public Boolean isProductAvailable(Long productId, Integer quantity) {
        return inventoryClient.isProductAvailable(productId, quantity);
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        UserResponse user= userClient.getUserById(request.userId());

        List<OrderItemResponse> itemResponses = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for(OrderItemRequest item : request.items()){
            ProductResponse product = productClient.getProductById(item.productId());
            Boolean available = inventoryClient.isProductAvailable(item.productId(), item.quantity());

            if (!available){
                throw new RuntimeException("Product " + item.productId() + " is not available");
            }

            BigDecimal itemTotal =product.price().multiply(BigDecimal.valueOf(item.quantity()));
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

        order.setUserId(request.userId());
        order.setStatus(OrderStatus.CREATED);
        order.setTotalAmount(totalAmount);

        Order savedOrder = orderRepository.save(order);

        for (OrderItemRequest item : request.items()) {

            ProductResponse product =
                    productClient.getProductById(item.productId());

            OrderItem orderItem = new OrderItem();

            orderItem.setOrderId(savedOrder.getId());
            orderItem.setProductId(item.productId());
            orderItem.setQuantity(item.quantity());
            orderItem.setPrice(product.price());

            orderItemRepository.save(orderItem);
        }

        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getUserId(),
                savedOrder.getStatus(),
                savedOrder.getTotalAmount(),
                itemResponses
        );
    }
}
