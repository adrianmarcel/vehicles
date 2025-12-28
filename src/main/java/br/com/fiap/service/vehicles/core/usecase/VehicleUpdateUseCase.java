package br.com.fiap.service.vehicles.core.usecase;

import br.com.fiap.service.vehicles.core.usecase.model.VehicleResponse;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleUpdateRequest;

public interface VehicleUpdateUseCase {

  VehicleResponse execute(VehicleUpdateRequest request);
}
