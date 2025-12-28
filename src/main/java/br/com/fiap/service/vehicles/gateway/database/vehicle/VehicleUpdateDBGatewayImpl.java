package br.com.fiap.service.vehicles.gateway.database.vehicle;

import br.com.fiap.service.vehicles.core.domain.shared.enums.Status;
import br.com.fiap.service.vehicles.core.domain.shared.utils.NumberUtils;
import br.com.fiap.service.vehicles.core.domain.shared.utils.StringUtils;
import br.com.fiap.service.vehicles.gateway.database.vehicle.repository.VehicleRepository;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.VehicleUpdateGateway;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayRequest;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayResponse;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleUpdateDBGatewayImpl implements VehicleUpdateGateway {

  private final VehicleRepository repository;

  @Override
  public Optional<VehicleGatewayResponse> execute(VehicleGatewayRequest request) {
    return repository
        .findById(request.getId())
        .map(
            entity -> {
              entity.setBrand(StringUtils.compare(request.getBrand(), entity.getBrand()));
              entity.setModel(StringUtils.compare(request.getModel(), entity.getModel()));
              entity.setColor(StringUtils.compare(request.getColor(), entity.getColor()));
              entity.setPrice(NumberUtils.compareBigDecimal(request.getPrice(), entity.getPrice()));
              entity.setStatus(Status.compare(request.getStatus(), entity.getStatus()));
              entity.setYear(NumberUtils.compareInteger(request.getYear(), entity.getYear()));
              entity.setUpdatedAt(request.getUpdatedAt());

              return VehicleGatewayResponse.mapper(repository.save(entity));
            });
  }
}
