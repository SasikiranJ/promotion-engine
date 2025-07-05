package com.promotion.promotion;

import com.promotion.model.CartItem;
import com.promotion.model.SKU;
import java.util.List;

public class ComboPromotion implements Promotion {
    private final SKU sku1;
    private final SKU sku2;
    private final int comboPrice;
    private final String description;

    public ComboPromotion(SKU sku1, SKU sku2, int comboPrice) {
        this(sku1, sku2, comboPrice, 
             String.format("Buy %s and %s together for $%d", 
                          sku1.getDisplayName(), sku2.getDisplayName(), comboPrice));
    }

    public ComboPromotion(SKU sku1, SKU sku2, int comboPrice, String description) {
        if (sku1 == null || sku2 == null) {
            throw new IllegalArgumentException();
        }
        if (sku1 == sku2) {
            throw new IllegalArgumentException();
        }
        if (comboPrice < 0) {
            throw new IllegalArgumentException();
        }
        this.sku1 = sku1;
        this.sku2 = sku2;
        this.comboPrice = comboPrice;
        this.description = description;
    }

    @Override
    public boolean isApplicable(List<CartItem> cartItems) {
        return getItem(cartItems, sku1) != null && getItem(cartItems, sku2) != null;
    }

    @Override
    public int apply(List<CartItem> cartItems) {
        CartItem item1 = getItem(cartItems, sku1);
        CartItem item2 = getItem(cartItems, sku2);
        if (item1 == null || item2 == null) {
            return 0;
        }
        int pairs = Math.min(item1.getQuantity(), item2.getQuantity());
        item1.deductQuantity(pairs);
        item2.deductQuantity(pairs);
        return pairs * comboPrice;
    }

    @Override
    public String getPromotionType() {
        return "COMBO_DISCOUNT";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getPriority() {
        return 75;
    }

    private CartItem getItem(List<CartItem> items, SKU sku) {
        return items.stream()
                .filter(i -> i.getSku() == sku && i.hasQuantity())
                .findFirst()
                .orElse(null);
    }

    public SKU getSku1() {
        return sku1;
    }

    public SKU getSku2() {
        return sku2;
    }

    public int getComboPrice() {
        return comboPrice;
    }

    @Override
    public String toString() {
        return String.format("ComboPromotion{sku1=%s, sku2=%s, comboPrice=%d, description='%s'}", 
                           sku1, sku2, comboPrice, description);
    }
}
