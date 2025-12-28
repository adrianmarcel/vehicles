package br.com.fiap.service.vehicles.core.usecase.model;

import br.com.fiap.service.vehicles.core.domain.shared.enums.Status;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleFilterRequest {

  private UUID id;
  private String brand;
  private String model;
  private String color;
  private Status status;
  private Integer minYear;
  private Integer maxYear;
  private BigDecimal minPrice;
  private BigDecimal maxPrice;
}
