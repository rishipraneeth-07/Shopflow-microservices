package com.shopflow.inventoryservice.kafka;

import com.shopflow.inventoryservice.event.OrderCreatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderEventConsumer {

    @KafkaListener(topics = "order-created")
    public void consumeOrderCreated(OrderCreatedEvent event) {
        System.out.println("Received order created event");
        System.out.println("Order ID: " + event.orderId());
        System.out.println("User ID: " + event.userId());
    }
}