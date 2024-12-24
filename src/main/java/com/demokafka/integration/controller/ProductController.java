package com.demokafka.integration.controller;

import com.demokafka.integration.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final KafkaProducerService kafkaProducerService;

    @Autowired
    public ProductController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping
    public String createProduct(@RequestBody Map<String, Object> payload) {
        // Print the received payload
        System.out.println("Received Payload: " + payload);

        // Convert the payload to a string (JSON format)
        String message = payload.toString();

        // Send the message to Kafka
        kafkaProducerService.sendMessage("moqui-product", message);

        // Return a success message
        return "Product data received and sent to Kafka!";
    }
}


