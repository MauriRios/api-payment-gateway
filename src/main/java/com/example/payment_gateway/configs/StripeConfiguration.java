package com.example.payment_gateway.configs;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfiguration {

    @Value("${stripe.secret-key}")
    public void setApiKey(String key) {
        Stripe.apiKey = key;
    }
}
