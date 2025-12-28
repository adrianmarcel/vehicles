package com.example.mainservice.dto;

import jakarta.validation.constraints.NotBlank;

public class PaymentWebhook {
    @NotBlank
    private String paymentCode;

    @NotBlank
    private String status;

    public String getPaymentCode() {
        return paymentCode;
    }

    public void setPaymentCode(String paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
