package br.com.fiap.service.vehicles.fixtures;

import br.com.fiap.service.vehicles.core.usecase.model.VehicleRequest;
import java.math.BigDecimal;

public class VehicleRequestFixture {

  public static VehicleRequest validRequest() {
    return VehicleRequest.builder()
        .brand("Mercedes Benz")
        .model("C300")
        .year(2023)
        .color("White")
        .price(new BigDecimal("300000"))
        .build();
  }
}
