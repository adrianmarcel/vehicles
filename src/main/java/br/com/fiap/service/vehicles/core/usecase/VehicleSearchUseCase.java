package br.com.fiap.service.vehicles.core.usecase;

import br.com.fiap.service.vehicles.core.usecase.model.VehicleFilterRequest;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleResponse;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VehicleSearchUseCase {

  Page<VehicleResponse> execute(@Valid VehicleFilterRequest request, Pageable pageable);
}
