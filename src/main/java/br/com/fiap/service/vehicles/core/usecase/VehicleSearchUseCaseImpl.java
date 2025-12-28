package br.com.fiap.service.vehicles.core.usecase;

import br.com.fiap.service.vehicles.core.usecase.model.VehicleFilterRequest;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleResponse;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.VehicleSearchGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleSearchUseCaseImpl implements VehicleSearchUseCase {

  private final VehicleSearchGateway searchAllGateway;

  @Override
  public Page<VehicleResponse> execute(VehicleFilterRequest filterRequest, Pageable pageable) {
    return searchAllGateway.execute(filterRequest, pageable).map(VehicleResponse::mapper);
  }
}
