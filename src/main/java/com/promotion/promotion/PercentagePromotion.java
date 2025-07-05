package com.promotion.promotion;

import com.promotion.model.CartItem;
import java.util.List;

public class PercentagePromotion implements Promotion {
    private final String description;
    private final double percentage;

    public PercentagePromotion(double percentage, String description) {
        this.percentage = percentage;
        this.description = description;
    }

    public double getPercentage() {
        return percentage;
    }

    @Override
    public boolean isApplicable(List<CartItem> cartItems) {
        return false;
    }

    @Override
    public int apply(List<CartItem> cartItems) {
        return 0;
    }

    @Override
    public String getPromotionType() {
        return "PERCENTAGE_DISCOUNT";
    }

    @Override
    public String getDescription() {
        return description;
    }
} 