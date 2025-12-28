package com.example.salesservice;

import com.example.salesservice.model.VehicleListing;
import com.example.salesservice.repository.SaleRepository;
import com.example.salesservice.repository.VehicleListingRepository;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SalesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehicleListingRepository vehicleListingRepository;

    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private RestTemplate mainRestTemplate;

    private MockRestServiceServer server;

    @BeforeEach
    void setup() {
        saleRepository.deleteAll();
        vehicleListingRepository.deleteAll();
        server = MockRestServiceServer.createServer(mainRestTemplate);
    }

    @Test
    void shouldListAndPurchase() throws Exception {
        mockMvc.perform(post("/internal/vehicles/sync")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"vehicle_id":1,"brand":"VW","model":"Gol","year":2021,"color":"azul","price":40000,"status":"available"}
                                """))
                .andExpect(status().isOk());

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));

        server.expect(MockRestRequestMatchers.requestTo("/internal/sales/reserve"))
                .andRespond(MockRestResponseCreators.withSuccess());

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"vehicleId":1,"buyerCpf":"11122233344","saleDate":"%s"}
                                """.formatted(LocalDate.now())))
                .andExpect(status().isCreated());

        mockMvc.perform(get("/vehicles"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void shouldUpdatePaymentStatus() throws Exception {
        VehicleListing listing = new VehicleListing();
        listing.setVehicleId(2L);
        listing.setBrand("Fiat");
        listing.setModel("Pulse");
        listing.setYear(2022);
        listing.setColor("vermelho");
        listing.setPrice(85000d);
        listing.setStatus("available");
        vehicleListingRepository.save(listing);

        server.expect(MockRestRequestMatchers.requestTo("/internal/sales/reserve"))
                .andRespond(MockRestResponseCreators.withSuccess());

        mockMvc.perform(post("/sales")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"vehicleId":2,"buyerCpf":"99988877766","saleDate":"%s"}
                                """.formatted(LocalDate.now())))
                .andExpect(status().isCreated());

        String paymentCode = saleRepository.findAll().get(0).getPaymentCode();

        mockMvc.perform(post("/internal/payments/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"payment_code":"%s","status":"paid"}
                                """.formatted(paymentCode)))
                .andExpect(status().isOk());

        mockMvc.perform(get("/sales"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].status", is("paid")));
    }
}
