package br.com.fiap.service.vehicles.gateway.database.vehicle;

import br.com.fiap.service.vehicles.gateway.database.vehicle.model.VehicleEntity;
import br.com.fiap.service.vehicles.gateway.database.vehicle.repository.VehicleRepository;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.VehicleCreateGateway;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayRequest;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleCreateDBGatewayImpl implements VehicleCreateGateway {

  private final VehicleRepository repository;

  @Override
  public VehicleGatewayResponse execute(VehicleGatewayRequest request) {
    return VehicleGatewayResponse.mapper(repository.save(VehicleEntity.mapper(request)));
  }
}
