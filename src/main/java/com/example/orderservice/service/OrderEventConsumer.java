package com.example.orderservice.service;

import com.example.orderservice.model.OrderEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class OrderEventConsumer {

    @KafkaListener(topics = "order-events", groupId = "order-group")
    public void consumeOrderEvent(OrderEvent orderEvent) {
        System.out.println("\n=================================");
        System.out.println("📥 EVENT RECEIVED FROM KAFKA");
        System.out.println("=================================");
        System.out.println("Event: " + orderEvent);

        // Process the order
        processOrder(orderEvent);

        System.out.println("=================================\n");
    }

    private void processOrder(OrderEvent orderEvent) {
        System.out.println("🔄 Processing order...");
        System.out.println("   Order ID: " + orderEvent.getOrderId());
        System.out.println("   Customer: " + orderEvent.getCustomerName());
        System.out.println("   Amount: " + orderEvent.getAmount() + " MAD");

        // Simulate different processing steps
        System.out.println("   ✓ Checking inventory...");
        System.out.println("   ✓ Generating invoice...");
        System.out.println("   ✓ Sending confirmation email...");
        System.out.println("✅ Order processed successfully!");
    }
}