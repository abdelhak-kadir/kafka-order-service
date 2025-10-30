package com.example.orderservice.service;

import com.example.orderservice.model.OrderEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderEventProducer {

    private static final String TOPIC = "order-events";

    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @Autowired
    public OrderEventProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendOrderEvent(OrderEvent orderEvent) {
        System.out.println("=================================");
        System.out.println("📤 SENDING EVENT TO KAFKA");
        System.out.println("Topic: " + TOPIC);
        System.out.println("Event: " + orderEvent);
        System.out.println("=================================");

        kafkaTemplate.send(TOPIC, orderEvent);

        System.out.println("✅ Event sent successfully!");
    }
}