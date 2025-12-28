package br.com.fiap.service.vehicles.gateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiap.service.vehicles.fixtures.VehicleEntityFixture;
import br.com.fiap.service.vehicles.fixtures.VehicleFilterRequestFixture;
import br.com.fiap.service.vehicles.gateway.database.vehicle.VehicleSearchDBGatewayImpl;
import br.com.fiap.service.vehicles.gateway.database.vehicle.repository.VehicleRepository;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.VehicleSearchGateway;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

@TestInstance(PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class VehicleSearchGatewayUnitTest {

  private VehicleSearchGateway gateway;

  @Mock private VehicleRepository repository;

  @BeforeEach
  public void setUp() {
    gateway = new VehicleSearchDBGatewayImpl(repository);
  }

  @Test
  public void testVehicleSearchAllWithSuccess() {
    when(repository.findAll(any(Specification.class), any(Pageable.class)))
        .thenReturn(new PageImpl<>(List.of(VehicleEntityFixture.validEntity())));

    assertDoesNotThrow(
        () -> {
          var result =
              gateway.execute(VehicleFilterRequestFixture.validRequest(), Pageable.unpaged());

          assertNotNull(result);
          assertNotNull(result.getContent());
          assertEquals(1, result.getSize());
        });
  }
}
