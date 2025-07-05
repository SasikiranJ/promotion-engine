package com.promotion.config;

import com.promotion.model.SKU;
import com.promotion.promotion.BulkPromotion;
import com.promotion.promotion.ComboPromotion;
import com.promotion.promotion.Promotion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for setting up promotion rules and application beans.
 */
@Configuration
public class PromotionConfig {
    
    private static final Logger logger = LoggerFactory.getLogger(PromotionConfig.class);

    
    @Bean
    public List<Promotion> promotions() {
        List<Promotion> promotions = List.of(
            new BulkPromotion(SKU.A, 3, 130, "Buy 3 Product A for $130"),
            
            new BulkPromotion(SKU.B, 2, 45, "Buy 2 Product B for $45"),
            
            new ComboPromotion(SKU.C, SKU.D, 30, "Buy Product C and Product D together for $30")
        );
        
        logger.info("Configured {} promotions", promotions.size());
        for (Promotion promotion : promotions) {
            logger.debug("Configured promotion: {} - {}", promotion.getPromotionType(), promotion.getDescription());
        }
        
        return promotions;
    }
} 