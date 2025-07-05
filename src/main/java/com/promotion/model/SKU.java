package com.promotion.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum SKU {
    A("Product A", 50),
    B("Product B", 30),
    C("Product C", 20),
    D("Product D", 15);

    private final String displayName;
    private final int unitPrice;

    SKU(String displayName, int unitPrice) {
        this.displayName = displayName;
        this.unitPrice = unitPrice;
    }

    @JsonValue
    public String getCode() {
        return this.name();
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getUnitPrice() {
        return unitPrice;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - $%d", displayName, name(), unitPrice);
    }
}
