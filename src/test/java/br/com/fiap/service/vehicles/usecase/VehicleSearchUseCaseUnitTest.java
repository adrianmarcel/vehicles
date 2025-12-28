package br.com.fiap.service.vehicles.usecase;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import br.com.fiap.service.vehicles.core.domain.shared.enums.Status;
import br.com.fiap.service.vehicles.core.usecase.VehicleSearchUseCase;
import br.com.fiap.service.vehicles.core.usecase.VehicleSearchUseCaseImpl;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleFilterRequest;
import br.com.fiap.service.vehicles.fixtures.VehicleFilterRequestFixture;
import br.com.fiap.service.vehicles.fixtures.VehicleGatewayResponseFixture;
import br.com.fiap.service.vehicles.gateway.domain.vehicle.VehicleSearchGateway;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@TestInstance(PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class VehicleSearchUseCaseUnitTest {

  private VehicleSearchUseCase useCase;

  @Mock private VehicleSearchGateway searchAllGateway;

  @BeforeEach
  public void setUp() {
    useCase = new VehicleSearchUseCaseImpl(searchAllGateway);
  }

  @Test
  public void testSearchAllVehiclesWithSuccessWhenReturnIsPageEmpty() {
    when(searchAllGateway.execute(any(VehicleFilterRequest.class), any(Pageable.class)))
        .thenReturn(Page.empty());

    var result = useCase.execute(VehicleFilterRequestFixture.validRequest(), Pageable.unpaged());

    assertTrue(result.getContent().isEmpty());
  }

  @Test
  public void testSearchAllVehiclesWithSuccess() {
    var responseGateway = new PageImpl<>(List.of(VehicleGatewayResponseFixture.validResponse()));

    when(searchAllGateway.execute(any(VehicleFilterRequest.class), any(Pageable.class)))
        .thenReturn(responseGateway);

    var result = useCase.execute(VehicleFilterRequestFixture.validRequest(), Pageable.unpaged());

    assertFalse(result.getContent().isEmpty());
    assertNotNull(result.getContent().getFirst());
    assertEquals(
        UUID.fromString("1cc73839-9b34-4158-9f93-789dc63a1cb2"),
        result.getContent().getFirst().getId());
    assertEquals("Mercedes Benz", result.getContent().getFirst().getBrand());
    assertEquals("C300", result.getContent().getFirst().getModel());
    assertEquals("White", result.getContent().getFirst().getColor());
    assertEquals(2023, result.getContent().getFirst().getYear());
    assertEquals(Status.AVAILABLE, result.getContent().getFirst().getStatus());
    assertEquals(new BigDecimal("300000"), result.getContent().getFirst().getPrice());
  }
}
