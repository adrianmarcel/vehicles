package com.example.mainservice.service;

import com.example.mainservice.dto.PaymentWebhook;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class SalesServiceClient {

    private final RestTemplate restTemplate;

    public SalesServiceClient(@Qualifier("salesRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void syncVehicle(Map<String, Object> payload) {
        restTemplate.postForEntity("/internal/vehicles/sync", payload, Void.class);
    }

    public void updatePaymentStatus(PaymentWebhook webhook, Long vehicleId, Double price) {
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(
                Map.of(
                        "payment_code", webhook.getPaymentCode(),
                        "status", webhook.getStatus(),
                        "vehicle_id", vehicleId,
                        "price", price
                )
        );
        restTemplate.exchange("/internal/payments/status", HttpMethod.POST, entity, Void.class);
    }
}
