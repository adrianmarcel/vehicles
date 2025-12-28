package com.example.mainservice.controller;

import com.example.mainservice.dto.PaymentWebhook;
import com.example.mainservice.dto.SaleSyncRequest;
import com.example.mainservice.dto.VehicleRequest;
import com.example.mainservice.dto.VehicleResponse;
import com.example.mainservice.dto.VehicleUpdateRequest;
import com.example.mainservice.service.VehicleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @PostMapping("/vehicles")
    @ResponseStatus(HttpStatus.CREATED)
    public VehicleResponse create(@Valid @RequestBody VehicleRequest request) {
        return vehicleService.create(request);
    }

    @GetMapping("/vehicles/{id}")
    public VehicleResponse find(@PathVariable Long id) {
        return vehicleService.find(id);
    }

    @PutMapping("/vehicles/{id}")
    public VehicleResponse update(@PathVariable Long id, @RequestBody VehicleUpdateRequest request) {
        return vehicleService.update(id, request);
    }

    @PostMapping("/internal/sales/reserve")
    public Map<String, String> reserve(@Valid @RequestBody SaleSyncRequest request) {
        return vehicleService.reserveForSale(request);
    }

    @PostMapping("/payments/webhook")
    public Map<String, String> webhook(@Valid @RequestBody PaymentWebhook webhook) {
        return vehicleService.handleWebhook(webhook);
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok", "service", "main");
    }
}
