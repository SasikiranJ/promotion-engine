package com.promotion.dto;

import com.promotion.model.SKU;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartItemDto {
    @NotNull(message = "SKU is required")
    private SKU sku;
    
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    public CartItemDto() {}

    public CartItemDto(SKU sku, int quantity) {
        this.sku = sku;
        this.quantity = quantity;
    }

    public SKU getSku() {
        return sku;
    }

    public void setSku(SKU sku) {
        this.sku = sku;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("CartItemDto{sku=%s, quantity=%d}", sku, quantity);
    }
} 