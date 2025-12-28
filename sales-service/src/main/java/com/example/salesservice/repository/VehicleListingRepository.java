package com.example.salesservice.repository;

import com.example.salesservice.model.VehicleListing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleListingRepository extends JpaRepository<VehicleListing, Long> {
    Optional<VehicleListing> findByVehicleId(Long vehicleId);
}
