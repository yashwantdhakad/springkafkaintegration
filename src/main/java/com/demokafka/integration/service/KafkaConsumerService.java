package com.demokafka.integration.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class KafkaConsumerService {

    private final RestTemplate restTemplate;

    @Autowired
    public KafkaConsumerService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    @KafkaListener(topics = "moqui-product", groupId = "moqui-group")
    public void consumeMessage(String message) {
        System.out.println("Consumed message: " + message);

        // Moqui REST endpoint
        String moquiEndpoint = "http://localhost:8080/rest/s1/commerce/productConsume";

        // Authentication credentials for Moqui REST API
        String username = "john.doe";
        String password = "moqui";

        // Set up authentication headers for Basic Authentication
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);  // Set content type to JSON
        String auth = username + ":" + password;
        String encodedAuth = new String(java.util.Base64.getEncoder().encode(auth.getBytes()));
        headers.set("Authorization", "Basic " + encodedAuth);

        // Create payload with Kafka message
        String payload = "{\"productData\": \"" + message + "\"}";  // Send message as JSON payload

        // Create HttpEntity with headers and payload
        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        // Call the Moqui REST API using RestTemplate
        ResponseEntity<String> response = restTemplate.exchange(moquiEndpoint, HttpMethod.POST, entity, String.class);

        // Process the response from Moqui
        System.out.println("Response from Moqui: " + response.getBody());
    }
}
