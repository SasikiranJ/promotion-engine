package com.promotion.service;

import com.promotion.dto.CheckoutRequest;
import com.promotion.dto.CheckoutResponse;
import com.promotion.model.CartItem;
import com.promotion.model.SKU;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

/**
 * Service for handling checkout operations and cart management.
 */
@Service
public class CheckoutService {
    
    private final PromotionEngine promotionEngine;

    public CheckoutService(PromotionEngine promotionEngine) {
        this.promotionEngine = promotionEngine;
    }

    /**
     * Processes a checkout request and returns detailed response.
     * 
     * @param request the checkout request
     * @return detailed checkout response
     */
    public CheckoutResponse processCheckout(CheckoutRequest request) {
        List<CartItem> cartItems = request.getCartItems() == null ? List.of() :
            request.getCartItems().stream()
                .map(dto -> new CartItem(dto.getSku(), dto.getQuantity()))
                .toList();
        return promotionEngine.calculateCheckoutWithBreakdown(cartItems);
    }

    /**
     * Gets a summary of available SKUs and their prices.
     * 
     * @return map of SKU codes to their unit prices
     */
    public Map<String, Integer> getAvailableSkus() {
        return Map.of(
            "A", SKU.A.getUnitPrice(),
            "B", SKU.B.getUnitPrice(),
            "C", SKU.C.getUnitPrice(),
            "D", SKU.D.getUnitPrice()
        );
    }
}
