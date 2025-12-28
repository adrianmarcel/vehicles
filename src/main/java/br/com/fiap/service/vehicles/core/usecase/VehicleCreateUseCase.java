package br.com.fiap.service.vehicles.core.usecase;

import br.com.fiap.service.vehicles.core.usecase.model.VehicleRequest;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleResponse;
import jakarta.validation.Valid;

public interface VehicleCreateUseCase {

  VehicleResponse execute(@Valid VehicleRequest request);
}
