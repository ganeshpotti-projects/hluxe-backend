package com.gk_dreams.HLuxe.strategy;

import com.gk_dreams.HLuxe.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStrategy {
    BigDecimal calculatePrice(Inventory inventory);
}
