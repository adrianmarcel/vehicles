package com.example.mainservice;

import com.example.mainservice.dto.PaymentWebhook;
import com.example.mainservice.dto.SaleSyncRequest;
import com.example.mainservice.dto.VehicleRequest;
import com.example.mainservice.model.Vehicle;
import com.example.mainservice.repository.PaymentRepository;
import com.example.mainservice.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate salesRestTemplate;

    private MockRestServiceServer server;

    @BeforeEach
    void setup() {
        vehicleRepository.deleteAll();
        paymentRepository.deleteAll();
        server = MockRestServiceServer.createServer(salesRestTemplate);
    }

    @Test
    void shouldCreateAndUpdateVehicle() throws Exception {
        server.expect(MockRestRequestMatchers.requestTo("/internal/vehicles/sync"))
                .andRespond(MockRestResponseCreators.withSuccess());

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"brand":"VW","model":"Gol","year":2020,"color":"azul","price":50000}
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status", is("available")));

        Vehicle stored = vehicleRepository.findAll().get(0);

        server.expect(MockRestRequestMatchers.requestTo("/internal/vehicles/sync"))
                .andRespond(MockRestResponseCreators.withSuccess());

        mockMvc.perform(put("/vehicles/" + stored.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"price":48000,"color":"preto"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price", is(48000.0)));
    }

    @Test
    void shouldReserveAndProcessWebhook() throws Exception {
        server.expect(MockRestRequestMatchers.requestTo("/internal/vehicles/sync"))
                .andRespond(MockRestResponseCreators.withSuccess());

        mockMvc.perform(post("/vehicles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"brand":"Ford","model":"Ka","year":2019,"color":"prata","price":30000}
                                """))
                .andExpect(status().isCreated());

        Vehicle vehicle = vehicleRepository.findAll().get(0);

        mockMvc.perform(post("/internal/sales/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"vehicleId":%d,"buyerCpf":"12345678900","saleDate":"%s","paymentCode":"abc-123"}
                                """.formatted(vehicle.getId(), LocalDate.now())))
                .andExpect(status().isOk());

        server.expect(MockRestRequestMatchers.requestTo("/internal/payments/status"))
                .andRespond(MockRestResponseCreators.withSuccess());

        mockMvc.perform(post("/payments/webhook")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"paymentCode":"abc-123","status":"paid"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicle_status", is("sold")));
    }
}
