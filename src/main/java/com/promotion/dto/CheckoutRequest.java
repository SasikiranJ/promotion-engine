package com.promotion.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import java.util.List;

public class CheckoutRequest {
    @Size(max = 100, message = "Cart cannot contain more than 100 items")
    @Valid
    private List<CartItemDto> cartItems;

    public CheckoutRequest() {}

    public CheckoutRequest(List<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }

    public List<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public String toString() {
        return String.format("CheckoutRequest{cartItems=%s}", cartItems);
    }
} 