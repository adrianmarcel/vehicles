package com.example.salesservice.service;

import com.example.salesservice.dto.ListingSync;
import com.example.salesservice.dto.PaymentStatusUpdate;
import com.example.salesservice.dto.SaleRequest;
import com.example.salesservice.model.Sale;
import com.example.salesservice.model.VehicleListing;
import com.example.salesservice.repository.SaleRepository;
import com.example.salesservice.repository.VehicleListingRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class SalesService {

    public static final String STATUS_AVAILABLE = "available";
    public static final String STATUS_RESERVED = "reserved";
    public static final String STATUS_SOLD = "sold";

    public static final String SALE_PENDING = "pending_payment";
    public static final String SALE_PAID = "paid";
    public static final String SALE_CANCELLED = "cancelled";

    private final VehicleListingRepository vehicleListingRepository;
    private final SaleRepository saleRepository;
    private final MainServiceClient mainServiceClient;

    public SalesService(VehicleListingRepository vehicleListingRepository, SaleRepository saleRepository, MainServiceClient mainServiceClient) {
        this.vehicleListingRepository = vehicleListingRepository;
        this.saleRepository = saleRepository;
        this.mainServiceClient = mainServiceClient;
    }

    public List<VehicleListing> listAvailable() {
        return vehicleListingRepository.findAll().stream()
                .filter(listing -> STATUS_AVAILABLE.equals(listing.getStatus()))
                .sorted((a, b) -> a.getPrice().compareTo(b.getPrice()))
                .toList();
    }

    public List<Sale> listPaid() {
        return saleRepository.findAll().stream()
                .filter(sale -> SALE_PAID.equals(sale.getStatus()))
                .sorted((a, b) -> a.getPrice().compareTo(b.getPrice()))
                .toList();
    }

    public Sale purchase(SaleRequest request) {
        VehicleListing listing = vehicleListingRepository.findByVehicleId(request.getVehicleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Vehicle not found for sale"));
        if (!STATUS_AVAILABLE.equals(listing.getStatus())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vehicle is not available");
        }

        String paymentCode = UUID.randomUUID().toString();
        Sale sale = new Sale();
        sale.setListing(listing);
        sale.setVehicleId(listing.getVehicleId());
        sale.setBuyerCpf(request.getBuyerCpf());
        sale.setSaleDate(request.getSaleDate());
        sale.setPaymentCode(paymentCode);
        sale.setStatus(SALE_PENDING);
        sale.setPrice(listing.getPrice());

        listing.setStatus(STATUS_RESERVED);
        vehicleListingRepository.save(listing);
        saleRepository.save(sale);

        mainServiceClient.reserveVehicle(listing.getVehicleId(), request.getBuyerCpf(), request.getSaleDate().toString(), paymentCode);
        return sale;
    }

    public Map<String, String> syncListing(ListingSync sync) {
        VehicleListing listing = vehicleListingRepository.findByVehicleId(sync.getVehicleId())
                .orElseGet(VehicleListing::new);
        listing.setVehicleId(sync.getVehicleId());
        listing.setBrand(sync.getBrand());
        listing.setModel(sync.getModel());
        listing.setYear(sync.getYear());
        listing.setColor(sync.getColor());
        listing.setPrice(sync.getPrice());
        listing.setStatus(sync.getStatus());
        vehicleListingRepository.save(listing);
        return Map.of("message", "Vehicle synced");
    }

    public Map<String, String> updatePaymentStatus(PaymentStatusUpdate update) {
        Sale sale = saleRepository.findByPaymentCode(update.getPaymentCode())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sale not found"));
        VehicleListing listing = sale.getListing();

        if (SALE_PAID.equals(update.getStatus())) {
            listing.setStatus(STATUS_SOLD);
            sale.setStatus(SALE_PAID);
        } else if (SALE_CANCELLED.equals(update.getStatus())) {
            listing.setStatus(STATUS_AVAILABLE);
            sale.setStatus(SALE_CANCELLED);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported payment status");
        }

        vehicleListingRepository.save(listing);
        saleRepository.save(sale);
        return Map.of("message", "Payment status synchronized");
    }
}
