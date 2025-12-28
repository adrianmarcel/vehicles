package br.com.fiap.service.vehicles.gateway;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiap.service.vehicles.fixtures.VehicleEntityFixture;
import br.com.fiap.service.vehicles.fixtures.VehicleGatewayRequestFixture;
import br.com.fiap.service.vehicles.gateway.database.vehicle.VehicleCreateDBGatewayImpl;
import br.com.fiap.service.vehicles.gateway.database.vehicle.model.VehicleEntity;
import br.com.fiap.service.vehicles.gateway.database.vehicle.repository.VehicleRepository;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.VehicleCreateGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class VehicleCreateGatewayUnitTest {

  private VehicleCreateGateway gateway;

  @Mock private VehicleRepository repository;

  @BeforeEach
  public void setUp() {
    gateway = new VehicleCreateDBGatewayImpl(repository);
  }

  @Test
  public void testVehicleCreateWithSuccess() {
    when(repository.save(any(VehicleEntity.class))).thenReturn(VehicleEntityFixture.validEntity());

    assertDoesNotThrow(
        () -> {
          var result = gateway.execute(VehicleGatewayRequestFixture.validRequest());

          assertNotNull(result);
        });
  }
}
