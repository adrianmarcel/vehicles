package com.example.salesservice.repository;

import com.example.salesservice.model.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findByPaymentCode(String paymentCode);
}
