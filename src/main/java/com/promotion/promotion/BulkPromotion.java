package com.promotion.promotion;

import com.promotion.model.CartItem;
import com.promotion.model.SKU;
import java.util.List;

public class BulkPromotion implements Promotion {
    private final SKU sku;
    private final int quantityRequired;
    private final int promotionalPrice;
    private final String description;

    public BulkPromotion(SKU sku, int quantityRequired, int promotionalPrice) {
        this(sku, quantityRequired, promotionalPrice, 
             String.format("Buy %d %s for $%d", quantityRequired, sku.getDisplayName(), promotionalPrice));
    }

    public BulkPromotion(SKU sku, int quantityRequired, int promotionalPrice, String description) {
        if (quantityRequired <= 0) {
            throw new IllegalArgumentException();
        }
        if (promotionalPrice < 0) {
            throw new IllegalArgumentException();
        }
        this.sku = sku;
        this.quantityRequired = quantityRequired;
        this.promotionalPrice = promotionalPrice;
        this.description = description;
    }

    @Override
    public boolean isApplicable(List<CartItem> cartItems) {
        return cartItems.stream()
                .anyMatch(item -> item.getSku() == sku && item.getQuantity() >= quantityRequired);
    }

    @Override
    public int apply(List<CartItem> cartItems) {
        for (CartItem item : cartItems) {
            if (item.getSku() == sku && item.hasQuantity()) {
                int applicableQty = item.getQuantity();
                int promoSets = applicableQty / quantityRequired;
                int total = promoSets * promotionalPrice;
                int remaining = applicableQty % quantityRequired;
                total += remaining * sku.getUnitPrice();
                item.deductQuantity(applicableQty);
                return total;
            }
        }
        return 0;
    }

    @Override
    public String getPromotionType() {
        return "BULK_DISCOUNT";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getPriority() {
        return 50;
    }

    public SKU getSku() {
        return sku;
    }

    public int getQuantityRequired() {
        return quantityRequired;
    }

    public int getPromotionalPrice() {
        return promotionalPrice;
    }

    @Override
    public String toString() {
        return String.format("BulkPromotion{sku=%s, quantityRequired=%d, promotionalPrice=%d, description='%s'}", 
                           sku, quantityRequired, promotionalPrice, description);
    }
}
