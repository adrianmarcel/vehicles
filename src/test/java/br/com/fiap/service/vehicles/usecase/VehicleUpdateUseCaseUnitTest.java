package br.com.fiap.service.vehicles.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiap.service.vehicles.core.domain.shared.enums.Status;
import br.com.fiap.service.vehicles.core.domain.shared.exception.BusinessException;
import br.com.fiap.service.vehicles.core.domain.shared.exception.ExceptionCode;
import br.com.fiap.service.vehicles.core.usecase.VehicleUpdateUseCase;
import br.com.fiap.service.vehicles.core.usecase.VehicleUpdateUseCaseImpl;
import br.com.fiap.service.vehicles.fixtures.VehicleGatewayResponseFixture;
import br.com.fiap.service.vehicles.fixtures.VehicleUpdateRequestFixture;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.VehicleUpdateGateway;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayRequest;
import java.math.BigDecimal;
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
public class VehicleUpdateUseCaseUnitTest {

  private VehicleUpdateUseCase useCase;

  @Mock private VehicleUpdateGateway updateGateway;

  @BeforeEach
  public void setUp() {
    useCase = new VehicleUpdateUseCaseImpl(updateGateway);
  }

  @Test
  public void testUpdateVehicleWithErrorWhenVehicleNotFound() {
    when(updateGateway.execute(any(VehicleGatewayRequest.class))).thenReturn(Optional.empty());

    var be =
        assertThrows(
            BusinessException.class,
            () -> useCase.execute(VehicleUpdateRequestFixture.validRequest()));

    assertEquals(ExceptionCode.VEHICLE_NOT_FOUND.getMessage(), be.getMessage());
    assertEquals(ExceptionCode.VEHICLE_NOT_FOUND.getCode().toString(), be.getCode());
  }

  @Test
  public void testUpdateVehicleWithSuccess() {
    when(updateGateway.execute(any(VehicleGatewayRequest.class)))
        .thenReturn(Optional.of(VehicleGatewayResponseFixture.validUpdatedResponse()));

    assertDoesNotThrow(
        () -> {
          var result = useCase.execute(VehicleUpdateRequestFixture.validRequest());

          assertNotNull(result);
          assertEquals(UUID.fromString("1cc73839-9b34-4158-9f93-789dc63a1cb2"), result.getId());
          assertEquals("Mercedes Benz", result.getBrand());
          assertEquals("C300", result.getModel());
          assertEquals("White", result.getColor());
          assertEquals(2025, result.getYear());
          assertEquals(Status.AVAILABLE, result.getStatus());
          assertEquals(new BigDecimal("300000"), result.getPrice());
        });
  }
}
