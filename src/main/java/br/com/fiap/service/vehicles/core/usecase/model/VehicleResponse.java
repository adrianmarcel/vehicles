package br.com.fiap.service.vehicles.core.usecase.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import br.com.fiap.service.vehicles.core.domain.shared.enums.Status;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class VehicleResponse {

  private UUID id;
  private String brand;
  private String model;
  private Integer year;
  private String color;
  private Status status;
  private BigDecimal price;
  private OffsetDateTime createdAt;

  @JsonInclude(NON_NULL)
  private OffsetDateTime updatedAt;

  @SneakyThrows
  public static VehicleResponse mapper(Object object) {
    var result = VehicleResponse.builder().build();
    BeanUtils.copyProperties(object, result);

    return result;
  }
}
