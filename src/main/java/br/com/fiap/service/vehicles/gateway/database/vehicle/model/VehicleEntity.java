package br.com.fiap.service.vehicles.gateway.database.vehicle.model;

import br.com.fiap.service.vehicles.core.domain.shared.enums.Status;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import org.springframework.beans.BeanUtils;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@Table(name = "vehicles")
public class VehicleEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID id;

  @Column private String brand;

  @Column private String model;

  @Column private String color;

  @Column
  @Enumerated(EnumType.STRING)
  private Status status;

  @Column private BigDecimal price;

  @Column(name = "vehicle_year")
  private Integer year;

  @Column private OffsetDateTime createdAt;

  @Column private OffsetDateTime updatedAt;

  public static VehicleEntity mapper(Object object) {
    var result = VehicleEntity.builder().build();
    BeanUtils.copyProperties(object, result);

    return result;
  }
}
