package br.com.fiap.service.vehicles.gateway.domain.vehicle;

import br.com.fiap.service.vehicles.core.usecase.model.VehicleFilterRequest;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleSearchGateway {

  Page<VehicleGatewayResponse> execute(VehicleFilterRequest filterRequest, Pageable pageable);
}
