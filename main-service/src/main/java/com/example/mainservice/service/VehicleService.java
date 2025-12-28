package com.example.mainservice.service;

import com.example.mainservice.dto.PaymentWebhook;
import com.example.mainservice.dto.SaleSyncRequest;
import com.example.mainservice.dto.VehicleRequest;
import com.example.mainservice.dto.VehicleResponse;
import com.example.mainservice.dto.VehicleUpdateRequest;
import com.example.mainservice.model.Payment;
import com.example.mainservice.model.Vehicle;
import com.example.mainservice.repository.PaymentRepository;
import com.example.mainservice.repository.VehicleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class VehicleService {

    public static final String STATUS_AVAILABLE = "available";
    public static final String STATUS_RESERVED = "reserved";
    public static final String STATUS_SOLD = "sold";

    public static final String PAYMENT_PENDING = "pending";
    public static final String PAYMENT_PAID = "paid";
    public static final String PAYMENT_CANCELLED = "cancelled";

    private final VehicleRepository vehicleRepository;
    private final PaymentRepository paymentRepository;
    private final SalesServiceClient salesServiceClient;

    public VehicleService(VehicleRepository vehicleRepository, PaymentRepository paymentRepository, SalesServiceClient salesServiceClient) {
        this.vehicleRepository = vehicleRepository;
        this.paymentRepository = paymentRepository;
        this.salesServiceClient = salesServiceClient;
    }

    public VehicleResponse create(VehicleRequest request) {
        Vehicle vehicle = new Vehicle();
        vehicle.setBrand(request.getBrand());
        vehicle.setModel(request.getModel());
        vehicle.setYear(request.getYear());
        vehicle.setColor(request.getColor());
        vehicle.setPrice(request.getPrice());
        vehicle.setStatus(STATUS_AVAILABLE);
        Vehicle saved = vehicleRepository.save(vehicle);

        salesServiceClient.syncVehicle(vehiclePayload(saved));
        return toResponse(saved);
    }

    public VehicleResponse update(Long id, VehicleUpdateRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));

        if (request.getBrand() != null) vehicle.setBrand(request.getBrand());
        if (request.getModel() != null) vehicle.setModel(request.getModel());
        if (request.getYear() != null) vehicle.setYear(request.getYear());
        if (request.getColor() != null) vehicle.setColor(request.getColor());
        if (request.getPrice() != null) vehicle.setPrice(request.getPrice());
        if (request.getStatus() != null) vehicle.setStatus(request.getStatus());

        Vehicle saved = vehicleRepository.save(vehicle);
        salesServiceClient.syncVehicle(vehiclePayload(saved));
        return toResponse(saved);
    }

    public VehicleResponse find(Long id) {
        return vehicleRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
    }

    public Map<String, String> reserveForSale(SaleSyncRequest request) {
        Vehicle vehicle = vehicleRepository.findById(request.getVehicleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found"));
        if (!STATUS_AVAILABLE.equals(vehicle.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehicle not available for sale");
        }
        vehicle.setStatus(STATUS_RESERVED);

        Payment payment = new Payment();
        payment.setVehicle(vehicle);
        payment.setBuyerCpf(request.getBuyerCpf());
        payment.setSaleDate(request.getSaleDate());
        payment.setPaymentCode(request.getPaymentCode());
        payment.setStatus(PAYMENT_PENDING);

        vehicleRepository.save(vehicle);
        paymentRepository.save(payment);
        return Map.of("message", "Vehicle reserved and payment pending");
    }

    public Map<String, String> handleWebhook(PaymentWebhook webhook) {
        Payment payment = paymentRepository.findByPaymentCode(webhook.getPaymentCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment not found"));
        Vehicle vehicle = payment.getVehicle();

        if (PAYMENT_PAID.equals(webhook.getStatus())) {
            vehicle.setStatus(STATUS_SOLD);
        } else if (PAYMENT_CANCELLED.equals(webhook.getStatus())) {
            vehicle.setStatus(STATUS_AVAILABLE);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported payment status");
        }

        payment.setStatus(webhook.getStatus());
        vehicleRepository.save(vehicle);
        paymentRepository.save(payment);

        salesServiceClient.updatePaymentStatus(webhook, vehicle.getId(), vehicle.getPrice());
        return Map.of("message", "Payment status updated", "vehicle_status", vehicle.getStatus());
    }

    private Map<String, Object> vehiclePayload(Vehicle vehicle) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("vehicle_id", vehicle.getId());
        payload.put("brand", vehicle.getBrand());
        payload.put("model", vehicle.getModel());
        payload.put("year", vehicle.getYear());
        payload.put("color", vehicle.getColor());
        payload.put("price", vehicle.getPrice());
        payload.put("status", vehicle.getStatus());
        return payload;
    }

    private VehicleResponse toResponse(Vehicle vehicle) {
        VehicleResponse response = new VehicleResponse();
        response.setId(vehicle.getId());
        response.setBrand(vehicle.getBrand());
        response.setModel(vehicle.getModel());
        response.setYear(vehicle.getYear());
        response.setColor(vehicle.getColor());
        response.setPrice(vehicle.getPrice());
        response.setStatus(vehicle.getStatus());
        response.setCreatedAt(vehicle.getCreatedAt());
        response.setUpdatedAt(vehicle.getUpdatedAt());
        return response;
    }
}
