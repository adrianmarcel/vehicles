package com.example.salesservice.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class MainServiceClient {

    private final RestTemplate restTemplate;

    public MainServiceClient(@Qualifier("mainRestTemplate") RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void reserveVehicle(Long vehicleId, String buyerCpf, String saleDate, String paymentCode) {
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(
                Map.of(
                        "vehicleId", vehicleId,
                        "buyerCpf", buyerCpf,
                        "saleDate", saleDate,
                        "paymentCode", paymentCode
                )
        );
        restTemplate.exchange("/internal/sales/reserve", HttpMethod.POST, entity, Void.class);
    }
}
