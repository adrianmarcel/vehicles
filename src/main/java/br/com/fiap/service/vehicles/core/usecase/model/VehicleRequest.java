package br.com.fiap.service.vehicles.core.usecase.model;

import java.math.BigDecimal;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class VehicleRequest {

  private String brand;
  private String model;
  private Integer year;
  private String color;
  private BigDecimal price;
}
