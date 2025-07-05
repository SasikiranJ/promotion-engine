package com.promotion.service;

import com.promotion.dto.CheckoutResponse;
import com.promotion.model.CartItem;
import com.promotion.promotion.Promotion;
import com.promotion.promotion.BulkPromotion;
import com.promotion.promotion.ComboPromotion;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PromotionEngine {
    private final List<Promotion> promotions;

    public PromotionEngine(List<Promotion> promotions) {
        this.promotions = new ArrayList<>(promotions);
        this.promotions.sort(Comparator.comparingInt(Promotion::getPriority));
    }

    public int applyPromotions(List<CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            return 0;
        }
        List<CartItem> clonedCart = cloneCartItems(cartItems);
        int total = 0;
        for (Promotion promotion : promotions) {
            while (promotion.isApplicable(clonedCart)) {
                int discount = promotion.apply(clonedCart);
                total += discount;
            }
        }
        for (CartItem remaining : clonedCart) {
            if (remaining.hasQuantity()) {
                int remainingTotal = remaining.getQuantity() * remaining.getSku().getUnitPrice();
                total += remainingTotal;
            }
        }
        return total;
    }

    public CheckoutResponse calculateCheckoutWithBreakdown(List<CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            return new CheckoutResponse(0, 0, 0, new ArrayList<>(), new ArrayList<>());
        }
        int originalTotal = cartItems.stream()
                .mapToInt(CartItem::getTotalPrice)
                .sum();
        List<CartItem> clonedCart = cloneCartItems(cartItems);
        List<CheckoutResponse.AppliedPromotion> appliedPromotions = new ArrayList<>();
        int totalPromotionsValue = 0;
        for (Promotion promotion : promotions) {
            int totalPromotionValue = 0;
            while (promotion.isApplicable(clonedCart)) {
                int promoValue = promotion.apply(clonedCart);
                totalPromotionValue += promoValue;
            }
            if (totalPromotionValue > 0) {
                totalPromotionsValue += totalPromotionValue;
                appliedPromotions.add(new CheckoutResponse.AppliedPromotion(
                    promotion.getPromotionType(),
                    promotion.getDescription(),
                    totalPromotionValue,
                    getAffectedSkus(promotion)
                ));
            }
        }
        int remainingTotal = 0;
        for (CartItem remaining : clonedCart) {
            if (remaining.hasQuantity()) {
                int remainingItemTotal = remaining.getQuantity() * remaining.getSku().getUnitPrice();
                remainingTotal += remainingItemTotal;
            }
        }
        int finalTotal = totalPromotionsValue + remainingTotal;
        int discountAmount = originalTotal - finalTotal;
        return new CheckoutResponse(originalTotal, discountAmount, finalTotal, cartItems, appliedPromotions);
    }

    public List<Promotion> getPromotions() {
        return new ArrayList<>(promotions);
    }

    private List<CartItem> cloneCartItems(List<CartItem> cartItems) {
        return cartItems.stream()
                .map(item -> new CartItem(item.getSku(), item.getQuantity()))
                .toList();
    }

    private List<String> getAffectedSkus(Promotion promotion) {
        Set<String> affectedSkus = new HashSet<>();
        if (promotion instanceof BulkPromotion bulkPromotion) {
            affectedSkus.add(bulkPromotion.getSku().name());
        } else if (promotion instanceof ComboPromotion comboPromotion) {
            affectedSkus.add(comboPromotion.getSku1().name());
            affectedSkus.add(comboPromotion.getSku2().name());
        }
        return new ArrayList<>(affectedSkus);
    }
}
