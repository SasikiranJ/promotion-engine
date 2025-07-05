package com.promotion.dto;

import com.promotion.model.CartItem;
import java.util.List;

public class CheckoutResponse {
    private final int originalTotal;
    private final int discountAmount;
    private final int finalTotal;
    private final List<CartItem> cartItems;
    private final List<AppliedPromotion> appliedPromotions;

    public CheckoutResponse(int originalTotal, int discountAmount, int finalTotal, 
                          List<CartItem> cartItems, List<AppliedPromotion> appliedPromotions) {
        this.originalTotal = originalTotal;
        this.discountAmount = discountAmount;
        this.finalTotal = finalTotal;
        this.cartItems = cartItems;
        this.appliedPromotions = appliedPromotions;
    }

    public int getOriginalTotal() {
        return originalTotal;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public int getFinalTotal() {
        return finalTotal;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public List<AppliedPromotion> getAppliedPromotions() {
        return appliedPromotions;
    }

    public static class AppliedPromotion {
        private final String promotionType;
        private final String description;
        private final int discountAmount;
        private final List<String> affectedSkus;

        public AppliedPromotion(String promotionType, String description, 
                              int discountAmount, List<String> affectedSkus) {
            this.promotionType = promotionType;
            this.description = description;
            this.discountAmount = discountAmount;
            this.affectedSkus = affectedSkus;
        }

        public String getPromotionType() {
            return promotionType;
        }

        public String getDescription() {
            return description;
        }

        public int getDiscountAmount() {
            return discountAmount;
        }

        public List<String> getAffectedSkus() {
            return affectedSkus;
        }
    }

    @Override
    public String toString() {
        return String.format("CheckoutResponse{originalTotal=%d, discountAmount=%d, finalTotal=%d, appliedPromotions=%s}", 
                           originalTotal, discountAmount, finalTotal, appliedPromotions);
    }
} 