package br.com.fiap.service.vehicles.core.usecase.model;

import br.com.fiap.service.vehicles.core.domain.shared.enums.Status;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class VehicleUpdateRequest {

  private UUID id;
  private String brand;
  private String model;
  private Integer year;
  private String color;
  private Status status;
  private BigDecimal price;
}
