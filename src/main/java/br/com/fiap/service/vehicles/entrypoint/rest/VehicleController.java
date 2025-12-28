package br.com.fiap.service.vehicles.entrypoint.rest;

import static org.springframework.http.ResponseEntity.created;
import static org.springframework.http.ResponseEntity.ok;

import br.com.fiap.service.vehicles.core.usecase.VehicleCreateUseCase;
import br.com.fiap.service.vehicles.core.usecase.VehicleSearchUseCase;
import br.com.fiap.service.vehicles.core.usecase.VehicleUpdateUseCase;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleFilterRequest;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleRequest;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleResponse;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleUpdateRequest;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(VehicleController.BASE_URI)
public class VehicleController {

  public static final String BASE_URI = "/fiap/v1/vehicles";

  private final VehicleCreateUseCase createUseCase;
  private final VehicleSearchUseCase searchUseCase;
  private final VehicleUpdateUseCase updateUseCase;

  @PostMapping
  public ResponseEntity<VehicleResponse> create(@Valid @RequestBody VehicleRequest request) {
    var response = createUseCase.execute(request);

    var uri = URI.create(BASE_URI.concat("/").concat(response.getId().toString()));

    return created(uri).body(response);
  }

  @GetMapping
  public ResponseEntity<Page<VehicleResponse>> read(
      @Valid VehicleFilterRequest filterRequest, Pageable pageable) {
    return ok(searchUseCase.execute(filterRequest, pageable));
  }

  @PutMapping("/{id}")
  public ResponseEntity<VehicleResponse> update(
      @PathVariable("id") UUID id, @Valid @RequestBody VehicleUpdateRequest request) {
    request.setId(id);
    return ok(updateUseCase.execute(request));
  }
}
