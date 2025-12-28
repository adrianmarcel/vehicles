package br.com.fiap.service.vehicles.gateway.domain.vehicle;

import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayRequest;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayResponse;

public interface VehicleCreateGateway {

  VehicleGatewayResponse execute(VehicleGatewayRequest request);
}
