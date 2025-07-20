package com.example.payment_gateway.configs;

import com.stripe.Stripe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class StripeConfiguration {

    @Value("${stripe.secret-key}")
    private String secretKey;

    @PostConstruct
    public void init() { Stripe.apiKey = secretKey; }
}
