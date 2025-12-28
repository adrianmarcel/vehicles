package br.com.fiap.service.vehicles.gateway.domain.vehicle.model;

import br.com.fiap.service.vehicles.core.domain.shared.enums.Status;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@AllArgsConstructor
@Builder(toBuilder = true)
public class VehicleGatewayRequest {

  private UUID id;
  private String brand;
  private String model;
  private String color;
  private Integer year;
  private Status status;
  private BigDecimal price;
  private OffsetDateTime createdAt;
  private OffsetDateTime updatedAt;

  public static VehicleGatewayRequest mapper(Object object) {
    var result = VehicleGatewayRequest.builder().build();
    BeanUtils.copyProperties(object, result);
    return result;
  }
}
