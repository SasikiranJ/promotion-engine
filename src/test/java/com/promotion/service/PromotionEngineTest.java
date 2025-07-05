package com.promotion.service;

import com.promotion.dto.CheckoutResponse;
import com.promotion.model.CartItem;
import com.promotion.model.SKU;
import com.promotion.promotion.BulkPromotion;
import com.promotion.promotion.ComboPromotion;
import com.promotion.promotion.Promotion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive tests for the PromotionEngine service.
 */
@DisplayName("Promotion Engine Tests")
class PromotionEngineTest {

    private PromotionEngine promotionEngine;
    private List<Promotion> promotions;

    @BeforeEach
    void setUp() {
        promotions = Arrays.asList(
                new BulkPromotion(SKU.A, 3, 130),
                new BulkPromotion(SKU.B, 2, 45),
                new ComboPromotion(SKU.C, SKU.D, 30)
        );
        promotionEngine = new PromotionEngine(promotions);
    }

    @Test
    @DisplayName("Scenario A: 1A, 1B, 1C should total 100")
    void testScenarioA() {
        List<CartItem> cart = Arrays.asList(
                new CartItem(SKU.A, 1),
                new CartItem(SKU.B, 1),
                new CartItem(SKU.C, 1)
        );
        
        int result = promotionEngine.applyPromotions(cart);
        assertEquals(100, result);
    }

    @Test
    @DisplayName("Scenario B: 5A, 5B, 1C should total 370")
    void testScenarioB() {
        List<CartItem> cart = Arrays.asList(
                new CartItem(SKU.A, 5),
                new CartItem(SKU.B, 5),
                new CartItem(SKU.C, 1)
        );
        
        int result = promotionEngine.applyPromotions(cart);
        assertEquals(370, result);
    }

    @Test
    @DisplayName("Scenario C: 3A, 5B, 1C, 1D should total 280")
    void testScenarioC() {
        List<CartItem> cart = Arrays.asList(
                new CartItem(SKU.A, 3),
                new CartItem(SKU.B, 5),
                new CartItem(SKU.C, 1),
                new CartItem(SKU.D, 1)
        );
        
        int result = promotionEngine.applyPromotions(cart);
        assertEquals(280, result);
    }

    @Test
    @DisplayName("Empty cart should return 0")
    void testEmptyCart() {
        List<CartItem> cart = Arrays.asList();
        int result = promotionEngine.applyPromotions(cart);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Null cart should return 0")
    void testNullCart() {
        int result = promotionEngine.applyPromotions(null);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Single item without promotion should return full price")
    void testSingleItemNoPromotion() {
        List<CartItem> cart = Arrays.asList(new CartItem(SKU.C, 1));
        int result = promotionEngine.applyPromotions(cart);
        assertEquals(20, result); 
    }

    @Test
    @DisplayName("Bulk promotion should apply correctly")
    void testBulkPromotion() {
        List<CartItem> cart = Arrays.asList(new CartItem(SKU.A, 3));
        int result = promotionEngine.applyPromotions(cart);
        assertEquals(130, result); 
    }

    @Test
    @DisplayName("Bulk promotion with remainder should calculate correctly")
    void testBulkPromotionWithRemainder() {
        List<CartItem> cart = Arrays.asList(new CartItem(SKU.A, 5));
        int result = promotionEngine.applyPromotions(cart);
        assertEquals(230, result); 
    }

    @Test
    @DisplayName("Combo promotion should apply correctly")
    void testComboPromotion() {
        List<CartItem> cart = Arrays.asList(
                new CartItem(SKU.C, 1),
                new CartItem(SKU.D, 1)
        );
        int result = promotionEngine.applyPromotions(cart);
        assertEquals(30, result); 
    }

    @Test
    @DisplayName("Combo promotion with different quantities should apply correctly")
    void testComboPromotionDifferentQuantities() {
        List<CartItem> cart = Arrays.asList(
                new CartItem(SKU.C, 2),
                new CartItem(SKU.D, 1)
        );
        int result = promotionEngine.applyPromotions(cart);
        assertEquals(50, result); 
    }

    @Test
    @DisplayName("Detailed checkout should return breakdown")
    void testDetailedCheckout() {
        List<CartItem> cart = Arrays.asList(
                new CartItem(SKU.A, 3),
                new CartItem(SKU.B, 2),
                new CartItem(SKU.C, 1),
                new CartItem(SKU.D, 1)
        );
        
        CheckoutResponse response = promotionEngine.calculateCheckoutWithBreakdown(cart);
        
        assertEquals(205, response.getFinalTotal());
        assertEquals(3, response.getAppliedPromotions().size());
        assertTrue(response.getOriginalTotal() > response.getFinalTotal());
    }

    @Test
    @DisplayName("Promotions should be sorted by priority")
    void testPromotionPriority() {
        List<Promotion> enginePromotions = promotionEngine.getPromotions();
        assertNotNull(enginePromotions);
        assertFalse(enginePromotions.isEmpty());
        
        for (int i = 0; i < enginePromotions.size() - 1; i++) {
            assertTrue(enginePromotions.get(i).getPriority() <= enginePromotions.get(i + 1).getPriority());
        }
    }
}
