package com.example.mainservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class SaleSyncRequest {
    @NotNull
    private Long vehicleId;

    @NotBlank
    private String buyerCpf;

    @NotNull
    private LocalDate saleDate;

    @NotBlank
    private String paymentCode;

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getBuyerCpf() {
        return buyerCpf;
    }

    public void setBuyerCpf(String buyerCpf) {
        this.buyerCpf = buyerCpf;
    }

    public LocalDate getSaleDate() {
        return saleDate;
    }

    public void setSaleDate(LocalDate saleDate) {
        this.saleDate = saleDate;
    }

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }
}
