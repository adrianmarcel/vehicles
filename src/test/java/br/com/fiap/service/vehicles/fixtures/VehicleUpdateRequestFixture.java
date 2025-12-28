package br.com.fiap.service.vehicles.fixtures;

import br.com.fiap.service.vehicles.core.domain.shared.enums.Status;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleUpdateRequest;
import java.math.BigDecimal;
import java.util.UUID;

public class VehicleUpdateRequestFixture {

  public static VehicleUpdateRequest validRequest() {
    return VehicleUpdateRequest.builder()
        .id(UUID.fromString("7025c696-92de-4ee8-9c28-bd11de8fc4ea"))
        .brand("Mercedes Benz")
        .model("C300")
        .year(2025)
        .color("White")
        .status(Status.SOLD)
        .price(new BigDecimal("300000"))
        .build();
  }
}
