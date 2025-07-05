package com.promotion.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.promotion.dto.CartItemDto;
import com.promotion.dto.CheckoutRequest;
import com.promotion.model.SKU;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the CheckoutController.
 */
@SpringBootTest
@AutoConfigureWebMvc
class CheckoutControllerIntegrationTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        // Test that the application context loads successfully
    }

    @Test
    void testCalculateCheckout_Success() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        CheckoutRequest request = new CheckoutRequest();
        request.setCartItems(Arrays.asList(
            new CartItemDto(SKU.A, 3),
            new CartItemDto(SKU.B, 2),
            new CartItemDto(SKU.C, 1),
            new CartItemDto(SKU.D, 1)
        ));

        mockMvc.perform(post("/api/v1/checkout/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalTotal").value(205))
                .andExpect(jsonPath("$.appliedPromotions").isArray())
                .andExpect(jsonPath("$.appliedPromotions.length()").value(3));
    }

    @Test
    void testCalculateCheckout_EmptyCart() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        CheckoutRequest request = new CheckoutRequest();
        request.setCartItems(Arrays.asList());

        mockMvc.perform(post("/api/v1/checkout/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.finalTotal").value(0));
    }

    @Test
    void testCalculateCheckout_InvalidRequest() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        // Invalid request with negative quantity
        CheckoutRequest request = new CheckoutRequest();
        request.setCartItems(Arrays.asList(
            new CartItemDto(SKU.A, -1)
        ));

        mockMvc.perform(post("/api/v1/checkout/calculate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetAvailableSkus() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        
        mockMvc.perform(get("/api/v1/checkout/skus"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.A").value(50))
                .andExpect(jsonPath("$.B").value(30))
                .andExpect(jsonPath("$.C").value(20))
                .andExpect(jsonPath("$.D").value(15));
    }
} 