package br.com.fiap.service.vehicles.usecase;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiap.service.vehicles.core.usecase.VehicleCreateUseCase;
import br.com.fiap.service.vehicles.core.usecase.VehicleCreateUseCaseImpl;
import br.com.fiap.service.vehicles.fixtures.VehicleGatewayResponseFixture;
import br.com.fiap.service.vehicles.fixtures.VehicleRequestFixture;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.VehicleCreateGateway;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.model.VehicleGatewayRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@TestInstance(PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class VehicleCreateUseCaseUnitTest {

  private VehicleCreateUseCase useCase;

  @Mock private VehicleCreateGateway createGateway;

  @BeforeEach
  public void setUp() {
    useCase = new VehicleCreateUseCaseImpl(createGateway);
  }

  @Test
  public void testVehicleCreateWithSuccess() {
    when(createGateway.execute(any(VehicleGatewayRequest.class)))
        .thenReturn(VehicleGatewayResponseFixture.validResponse());

    assertDoesNotThrow(() -> useCase.execute(VehicleRequestFixture.validRequest()));
  }
}
