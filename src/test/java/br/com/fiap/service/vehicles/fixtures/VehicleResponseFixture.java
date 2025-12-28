package br.com.fiap.service.vehicles.fixtures;

import br.com.fiap.service.vehicles.core.usecase.model.VehicleResponse;
import java.math.BigDecimal;
import java.util.UUID;

public class VehicleResponseFixture {

  public static VehicleResponse validResponse() {
    return VehicleResponse.builder()
        .id(UUID.fromString("1cc73839-9b34-4158-9f93-789dc63a1cb2"))
        .brand("Mercedes Benz")
        .model("C300")
        .year(2023)
        .color("White")
        .price(new BigDecimal("300000"))
        .build();
  }

  public static VehicleResponse validUpdatedResponse() {
    return VehicleResponse.builder()
        .id(UUID.fromString("1cc73839-9b34-4158-9f93-789dc63a1cb2"))
        .brand("Mercedes Benz")
        .model("C300")
        .year(2025)
        .color("White")
        .price(new BigDecimal("300000"))
        .build();
  }
}
