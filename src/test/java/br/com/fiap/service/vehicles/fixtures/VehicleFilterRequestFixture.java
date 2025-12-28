package br.com.fiap.service.vehicles.fixtures;

import br.com.fiap.service.vehicles.core.domain.shared.enums.Status;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleFilterRequest;
import java.math.BigDecimal;

public class VehicleFilterRequestFixture {

  public static VehicleFilterRequest validRequest() {
    return VehicleFilterRequest.builder()
        .brand("Mercedes Benz")
        .model("C300")
        .color("White")
        .status(Status.AVAILABLE)
        .minYear(2020)
        .maxYear(2026)
        .minPrice(new BigDecimal("100000"))
        .maxPrice(new BigDecimal("300000"))
        .build();
  }
}
