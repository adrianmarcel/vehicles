package br.com.fiap.service.vehicles.gateway.database.vehicle;

import br.com.fiap.service.vehicles.core.usecase.model.VehicleFilterRequest;
import br.com.fiap.service.vehicles.gateway.database.vehicle.repository.VehicleRepository;
import br.com.fiap.service.vehicles.gateway.database.vehicle.specification.VehicleSpecification;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.VehicleSearchGateway;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleSearchDBGatewayImpl implements VehicleSearchGateway {

  private final VehicleRepository repository;

  @Override
  public Page<VehicleGatewayResponse> execute(
      VehicleFilterRequest filterRequest, Pageable pageable) {
    return repository
        .findAll(VehicleSpecification.build(filterRequest), pageable)
        .map(VehicleGatewayResponse::mapper);
  }
}
