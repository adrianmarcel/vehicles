package br.com.fiap.service.vehicles.core.usecase;

import br.com.fiap.service.vehicles.core.domain.shared.enums.Status;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleRequest;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleResponse;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.VehicleCreateGateway;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayRequest;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleCreateUseCaseImpl implements VehicleCreateUseCase {

  private final VehicleCreateGateway createGateway;

  @Override
  public VehicleResponse execute(VehicleRequest request) {
    var gatewayRequest = VehicleGatewayRequest.mapper(request);
    gatewayRequest.setStatus(Status.AVAILABLE);
    gatewayRequest.setCreatedAt(OffsetDateTime.now());
    gatewayRequest.setUpdatedAt(OffsetDateTime.now());

    var gatewayResponse = createGateway.execute(gatewayRequest);

    return VehicleResponse.mapper(gatewayResponse);
  }
}
