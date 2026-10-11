package com.shopflow.inventoryservice.kafka;

import com.shopflow.inventoryservice.event.OrderCreatedEvent;
import com.shopflow.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventConsumer {

    private final InventoryService inventoryService;

    @KafkaListener(topics = "order-created")
    public void consumeOrderCreated(OrderCreatedEvent event) {
        System.out.println("Received order created event: " + event.orderId());

        event.items().forEach(item ->
                inventoryService.reserveInventory(
                        item.productId(),
                        item.quantity()
                )
        );
    }
}