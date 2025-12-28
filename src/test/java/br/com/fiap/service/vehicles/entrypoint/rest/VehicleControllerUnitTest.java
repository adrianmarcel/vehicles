package br.com.fiap.service.vehicles.entrypoint.rest;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import br.com.fiap.service.vehicles.core.domain.shared.exception.BusinessException;
import br.com.fiap.service.vehicles.core.domain.shared.exception.ExceptionCode;
import br.com.fiap.service.vehicles.core.domain.shared.exception.VehicleExceptionHandler;
import br.com.fiap.service.vehicles.core.usecase.VehicleCreateUseCase;
import br.com.fiap.service.vehicles.core.usecase.VehicleSearchUseCase;
import br.com.fiap.service.vehicles.core.usecase.VehicleUpdateUseCase;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleRequest;
import br.com.fiap.service.vehicles.core.usecase.model.VehicleUpdateRequest;
import br.com.fiap.service.vehicles.fixtures.VehicleFilterRequestFixture;
import br.com.fiap.service.vehicles.fixtures.VehicleRequestFixture;
import br.com.fiap.service.vehicles.fixtures.VehicleResponseFixture;
import br.com.fiap.service.vehicles.fixtures.VehicleUpdateRequestFixture;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@TestInstance(PER_CLASS)
@WebMvcTest(VehicleController.class)
@Import(VehicleExceptionHandler.class)
public class VehicleControllerUnitTest {

  @Autowired private WebApplicationContext context;

  @Autowired @MockBean private VehicleCreateUseCase createUseCase;
  @Autowired @MockBean private VehicleSearchUseCase searchUseCase;
  @Autowired @MockBean private VehicleUpdateUseCase updateUseCase;

  private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void testCreateVehicleWithErrorWhenInvalidPath() throws Exception {
    mockMvc
        .perform(
            post("/fiap/v1/vehiclessss")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VehicleRequestFixture.validRequest()))
                .characterEncoding("UTF-8"))
        .andExpect(status().isNotFound());
  }

  @Test
  public void testCreateVehicleWithSuccess() throws Exception {
    when(createUseCase.execute(any(VehicleRequest.class)))
        .thenReturn(VehicleResponseFixture.validResponse());

    mockMvc
        .perform(
            post("/fiap/v1/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(VehicleRequestFixture.validRequest()))
                .characterEncoding("UTF-8"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.id").value("1cc73839-9b34-4158-9f93-789dc63a1cb2"))
        .andExpect(jsonPath("$.brand").value("Mercedes Benz"))
        .andExpect(jsonPath("$.model").value("C300"))
        .andExpect(jsonPath("$.year").value(2023))
        .andExpect(jsonPath("$.color").value("White"))
        .andExpect(jsonPath("$.price").value(300000));
  }

  @Test
  public void testReadAllVehiclesWithSuccessWhenReturnIsEmpty() throws Exception {
    when(searchUseCase.execute(VehicleFilterRequestFixture.validRequest(), Pageable.unpaged()))
        .thenReturn(new PageImpl<>(Collections.emptyList()));

    mockMvc
        .perform(
            get("/fiap/v1/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
        .andExpect(status().isOk());
  }

  @Test
  public void testReadAllVehiclesWithSuccess() throws Exception {
    when(searchUseCase.execute(VehicleFilterRequestFixture.validRequest(), Pageable.unpaged()))
        .thenReturn(
            new PageImpl<>(Collections.singletonList(VehicleResponseFixture.validResponse())));

    mockMvc
        .perform(
            get("/fiap/v1/vehicles")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
        .andExpect(status().isOk());
  }

  @Test
  public void testUpdateVehicleWithErrorWhenVehicleNotFound() throws Exception {
    var exception = new BusinessException(ExceptionCode.VEHICLE_NOT_FOUND);
    when(updateUseCase.execute(any(VehicleUpdateRequest.class))).thenThrow(exception);

    mockMvc
        .perform(
            put("/fiap/v1/vehicles/{id}", "7025c696-92de-4ee8-9c28-bd11de8fc4ea")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(VehicleUpdateRequestFixture.validRequest()))
                .characterEncoding("UTF-8"))
        .andExpect(status().isNotFound())
        .andExpect(jsonPath("$.errors[0].code").value(exception.getCode()))
        .andExpect(jsonPath("$.errors[0].message").value(exception.getMessage()))
        .andExpect(jsonPath("$.code").value(exception.getHttpStatusCode().value()))
        .andExpect(jsonPath("$.message").value(exception.getHttpStatusCode().getReasonPhrase()));
  }

  @Test
  public void testUpdateVehicleWithSuccess() throws Exception {
    when(updateUseCase.execute(any(VehicleUpdateRequest.class)))
        .thenReturn(VehicleResponseFixture.validUpdatedResponse());

    mockMvc
        .perform(
            put("/fiap/v1/vehicles/{id}", "1cc73839-9b34-4158-9f93-789dc63a1cb2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(VehicleUpdateRequestFixture.validRequest()))
                .characterEncoding("UTF-8"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").value("1cc73839-9b34-4158-9f93-789dc63a1cb2"))
        .andExpect(jsonPath("$.brand").value("Mercedes Benz"))
        .andExpect(jsonPath("$.model").value("C300"))
        .andExpect(jsonPath("$.color").value("White"))
        .andExpect(jsonPath("$.year").value(2025))
        .andExpect(jsonPath("$.price").value(300000));
  }
}
