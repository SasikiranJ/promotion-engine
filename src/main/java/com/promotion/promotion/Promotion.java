package com.promotion.promotion;

import com.promotion.model.CartItem;
import java.util.List;

public interface Promotion {
    boolean isApplicable(List<CartItem> cartItems);
    
    int apply(List<CartItem> cartItems);
    
    String getPromotionType();
    
    String getDescription();
    
    default int getPriority() {
        return 100;
    }
}
