package com.promotion.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class CartItem {
    private Long id;
    
    @NotNull(message = "SKU is required")
    private SKU sku;
    
    @Min(value = 1, message = "Quantity must be at least 1")
    private int quantity;

    public CartItem() {}

    public CartItem(SKU sku, int quantity) {
        this.sku = sku;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void deductQuantity(int qty) {
        if (qty > this.quantity) {
            throw new IllegalArgumentException();
        }
        this.quantity -= qty;
    }

    public int getTotalPrice() {
        return this.sku.getUnitPrice() * this.quantity;
    }

    public boolean hasQuantity() {
        return this.quantity > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return quantity == cartItem.quantity && sku == cartItem.sku;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(sku, quantity);
    }

    @Override
    public String toString() {
        return String.format("CartItem{sku=%s, quantity=%d, totalPrice=%d}", sku, quantity, getTotalPrice());
    }
}
