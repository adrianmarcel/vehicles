package com.example.salesservice.controller;

import com.example.salesservice.dto.ListingSync;
import com.example.salesservice.dto.PaymentStatusUpdate;
import com.example.salesservice.dto.SaleRequest;
import com.example.salesservice.model.Sale;
import com.example.salesservice.model.VehicleListing;
import com.example.salesservice.service.SalesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class SalesController {

    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @GetMapping("/vehicles")
    public List<VehicleListing> listAvailable() {
        return salesService.listAvailable();
    }

    @GetMapping("/sales")
    public List<Sale> listPaid() {
        return salesService.listPaid();
    }

    @PostMapping("/sales")
    @ResponseStatus(HttpStatus.CREATED)
    public Sale purchase(@Valid @RequestBody SaleRequest request) {
        return salesService.purchase(request);
    }

    @PostMapping("/internal/vehicles/sync")
    public Map<String, String> sync(@Valid @RequestBody ListingSync sync) {
        return salesService.syncListing(sync);
    }

    @PostMapping("/internal/payments/status")
    public Map<String, String> paymentStatus(@Valid @RequestBody PaymentStatusUpdate update) {
        return salesService.updatePaymentStatus(update);
    }

    @GetMapping("/health")
    public Map<String, String> health() {
        return Map.of("status", "ok", "service", "sales");
    }
}
