package br.com.fiap.service.vehicles.gateway.domain.vehicle;

import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayRequest;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayResponse;
import java.util.Optional;

public interface VehicleUpdateGateway {

  Optional<VehicleGatewayResponse> execute(VehicleGatewayRequest request);
}
