package com.api.controller.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.api.controller.ExchangeController;
import com.api.model.Exchange;
import com.api.service.ExchangeService;

//@WebMvcTest(ExchangeController.class)
@SpringBootTest
@AutoConfigureMockMvc
class ExchangeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExchangeService service;

    private Exchange exchange;

    @BeforeEach
    void setUp() {
        exchange = new Exchange();
        exchange.setId(1);
        exchange.setName("NYSE");
        exchange.setCountry("USA");
        exchange.setDescription("New York Stock Exchange");
    }

    @Test
    void testAddingExchangeStock() throws Exception {
        when(service.saveExchangeDetails(any(Exchange.class))).thenReturn(exchange);

        String json = """
                {
                  "id": 1,
                  "name": "NYSE",
                  "country": "USA",
                  "description": "New York Stock Exchange"
                }
                """;

        mockMvc.perform(post("/exchange/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().string("Exchange stock saved succesfully"));

        verify(service, times(1)).saveExchangeDetails(any(Exchange.class));
    }

    @Test
    void testFetchExchangeDetails() throws Exception {
        when(service.getOneExchangeDetails(1)).thenReturn(exchange);

        mockMvc.perform(get("/exchange/get/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NYSE"))
                .andExpect(jsonPath("$.country").value("USA"));

        verify(service, times(1)).getOneExchangeDetails(1);
    }

    @Test
    void testFetchAllExchangeDetails() throws Exception {
        List<Exchange> list = Arrays.asList(exchange);
        when(service.getAllExchangeDetails()).thenReturn(list);

        mockMvc.perform(get("/exchange/getAllExchanges"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("NYSE"))
                .andExpect(jsonPath("$[0].country").value("USA"));

        verify(service, times(1)).getAllExchangeDetails();
    }

    @Test
    void testUpdateExchangeDetails() throws Exception {
        doNothing().when(service).updateExchangeDetails(any(Exchange.class));

        String json = """
                {
                  "id": 1,
                  "name": "BSE",
                  "country": "India",
                  "description": "Bombay Stock Exchange"
                }
                """;

        mockMvc.perform(put("/exchange/updateExchange")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().string("Exchange updated successfully."));

        verify(service, times(1)).updateExchangeDetails(any(Exchange.class));
    }

    @Test
    void testDeleteOneExchange() throws Exception {
        doNothing().when(service).deleteOneExchange(1);

        mockMvc.perform(delete("/exchange/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Exchange with 1 deleted succesfully"));

        verify(service, times(1)).deleteOneExchange(1);
    }

    @Test
    void testDeleteAllExchanges() throws Exception {
        doNothing().when(service).deleteAllExchangeDetails();

        mockMvc.perform(delete("/exchange/deleteAll"))
                .andExpect(status().isOk())
                .andExpect(content().string("All Exchanges deleted successfully.."));

        verify(service, times(1)).deleteAllExchangeDetails();
    }

    /*@Test
    void testGetStockForExchange() throws Exception {
        Object mockStock = new Object();
        when(service.getStockForExchange(100)).thenReturn(mockStock);

        mockMvc.perform(get("/exchange/1/stock/100"))
                .andExpect(status().isOk());

        verify(service, times(1)).getStockForExchange(100);
    }*/
/*
    @Test
    void testGetUserForExchange() throws Exception {
        Object mockUser = new Object();
        when(service.getUserForExchange(200)).thenReturn(mockUser);

        mockMvc.perform(get("/exchange/1/user/1")
                .accept(MediaType.APPLICATION_JSON))   // 👈 tell Spring you want JSON
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON));


        verify(service, times(1)).getUserForExchange(200);
    }*/
}
