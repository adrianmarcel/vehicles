package br.com.fiap.service.vehicles.fixtures;

import br.com.fiap.service.vehicles.core.domain.shared.enums.Status;
import br.com.fiap.service.vehicles.gateway.database.vehicle.model.VehicleEntity;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

public class VehicleEntityFixture {

  public static VehicleEntity validEntity() {
    return VehicleEntity.builder()
        .id(UUID.fromString("1cc73839-9b34-4158-9f93-789dc63a1cb2"))
        .brand("Mercedes Benz")
        .model("C300")
        .color("White")
        .year(2023)
        .status(Status.AVAILABLE)
        .price(new BigDecimal("300000"))
        .createdAt(OffsetDateTime.of(2025, 12, 31, 10, 11, 12, 1234, ZoneOffset.UTC))
        .createdAt(OffsetDateTime.of(2025, 12, 31, 10, 15, 12, 1234, ZoneOffset.UTC))
        .build();
  }

  public static VehicleEntity validUpdatedEntity() {
    return VehicleEntity.builder()
        .id(UUID.fromString("1cc73839-9b34-4158-9f93-789dc63a1cb2"))
        .brand("Mercedes Benz")
        .model("C300")
        .color("White")
        .year(2025)
        .status(Status.AVAILABLE)
        .price(new BigDecimal("300000"))
        .createdAt(OffsetDateTime.of(2025, 12, 31, 10, 11, 12, 1234, ZoneOffset.UTC))
        .createdAt(OffsetDateTime.of(2025, 12, 31, 10, 15, 12, 1234, ZoneOffset.UTC))
        .build();
  }
}
