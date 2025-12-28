package br.com.fiap.service.vehicles.gateway;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import br.com.fiap.service.vehicles.fixtures.VehicleEntityFixture;
import br.com.fiap.service.vehicles.fixtures.VehicleGatewayRequestFixture;
import br.com.fiap.service.vehicles.gateway.database.vehicle.VehicleUpdateDBGatewayImpl;
import br.com.fiap.service.vehicles.gateway.database.vehicle.model.VehicleEntity;
import br.com.fiap.service.vehicles.gateway.database.vehicle.repository.VehicleRepository;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.VehicleUpdateGateway;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class VehicleUpdateGatewayUnitTest {

  private VehicleUpdateGateway gateway;

  @Mock private VehicleRepository repository;

  @BeforeEach
  public void setUp() {
    gateway = new VehicleUpdateDBGatewayImpl(repository);
  }

  @Test
  public void testVehicleUpdateWithSuccess() {
    when(repository.findById(eq(UUID.fromString("1cc73839-9b34-4158-9f93-789dc63a1cb2"))))
        .thenReturn(Optional.of(VehicleEntityFixture.validEntity()));

    when(repository.save(any(VehicleEntity.class)))
        .thenReturn(VehicleEntityFixture.validUpdatedEntity());

    assertDoesNotThrow(
        () -> {
          var result = gateway.execute(VehicleGatewayRequestFixture.validRequest());

          assertTrue(result.isPresent());
        });
  }
}
