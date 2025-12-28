package br.com.fiap.service.vehicles.fixtures;

import br.com.fiap.service.vehicles.core.domain.shared.enums.Status;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayRequest;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

public class VehicleGatewayRequestFixture {

  public static VehicleGatewayRequest validRequest() {
    return VehicleGatewayRequest.builder()
        .id(UUID.fromString("1cc73839-9b34-4158-9f93-789dc63a1cb2"))
        .brand("Mercedes Benz")
        .model("C300")
        .year(2023)
        .color("White")
        .status(Status.AVAILABLE)
        .price(new BigDecimal("300000"))
        .createdAt(OffsetDateTime.now())
        .updatedAt(OffsetDateTime.now().plusMinutes(1L))
        .build();
  }
}
