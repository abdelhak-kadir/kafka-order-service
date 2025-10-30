package com.example.orderservice.controller;

import com.example.orderservice.model.OrderEvent;
import com.example.orderservice.service.OrderEventProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderEventProducer producer;

    @Autowired
    public OrderController(OrderEventProducer producer) {
        this.producer = producer;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, String>> createOrder(@RequestBody OrderEvent orderEvent) {
        try {
            producer.sendOrderEvent(orderEvent);

            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Order event sent successfully!");
            response.put("orderId", orderEvent.getOrderId());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            Map<String, String> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Failed to send order event: " + e.getMessage());

            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "UP");
        response.put("service", "Order Service");
        return ResponseEntity.ok(response);
    }
}