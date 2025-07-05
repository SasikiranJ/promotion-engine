package com.promotion.controller;

import com.promotion.dto.CheckoutRequest;
import com.promotion.dto.CheckoutResponse;
import com.promotion.service.CheckoutService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

/**
 * REST controller for checkout operations.
 * All endpoints use DTOs for input/output.
 */
@RestController
@RequestMapping("/api/v1/checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    /**
     * Calculate checkout total with promotions.
     * @param request CheckoutRequest DTO
     * @return CheckoutResponse DTO
     */
    @PostMapping("/calculate")
    public ResponseEntity<CheckoutResponse> calculateCheckout(@Valid @RequestBody CheckoutRequest request) {
        CheckoutResponse response = checkoutService.processCheckout(request);
        return ResponseEntity.ok(response);
    }

    /**
     * Get available SKUs and their prices.
     * @return Map of SKU code to price
     */
    @GetMapping("/skus")
    public ResponseEntity<Map<String, Integer>> getAvailableSkus() {
        Map<String, Integer> skus = checkoutService.getAvailableSkus();
        return ResponseEntity.ok(skus);
    }
} 