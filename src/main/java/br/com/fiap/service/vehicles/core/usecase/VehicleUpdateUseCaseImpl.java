package br.com.fiap.service.vehicles.core.usecase;

import br.com.fiap.service.vehicles.core.domain.shared.exception.BusinessException;
import br.com.fiap.service.vehicles.core.domain.shared.exception.ExceptionCode;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleResponse;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleUpdateRequest;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.VehicleUpdateGateway;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayRequest;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleUpdateUseCaseImpl implements VehicleUpdateUseCase {

  private final VehicleUpdateGateway updateGateway;

  @Override
  public VehicleResponse execute(VehicleUpdateRequest request) {
    var gatewayRequest = VehicleGatewayRequest.mapper(request);
    gatewayRequest.setUpdatedAt(OffsetDateTime.now());

    return updateGateway
        .execute(gatewayRequest)
        .map(VehicleResponse::mapper)
        .orElseThrow(() -> new BusinessException(ExceptionCode.VEHICLE_NOT_FOUND));
  }
}
