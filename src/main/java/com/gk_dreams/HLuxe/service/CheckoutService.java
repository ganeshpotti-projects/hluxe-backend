package com.gk_dreams.HLuxe.service;

import com.gk_dreams.HLuxe.entity.Booking;

public interface CheckoutService {
    String getCheckoutSession(Booking booking, String successUrl, String failureUrl);
}
