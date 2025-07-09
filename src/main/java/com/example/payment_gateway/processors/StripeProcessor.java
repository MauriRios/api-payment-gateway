package com.example.payment_gateway.processors;

import com.example.payment_gateway.models.dtos.PaymentResponseDTO;
import com.example.payment_gateway.models.providersdata.AbstractPaymentData;
import com.example.payment_gateway.models.providersdata.StripePaymentData;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StripeProcessor implements PaymentProcessor {

    @Override
    public String getProvider() {
        return "STRIPE";
    }

    @Override
    public PaymentResponseDTO initiatePayment(AbstractPaymentData data) {
        StripePaymentData stripeData = (StripePaymentData) data;

        // Validación básica
        if (stripeData.getCurrency() == null || stripeData.getCurrency().isBlank()) {
            throw new IllegalArgumentException("Currency is required for Stripe payment.");
        }

        List<SessionCreateParams.LineItem> lineItems = stripeData.getItems().stream()
                .map(item -> SessionCreateParams.LineItem.builder()
                        .setQuantity((long) item.getQuantity())
                        .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency(stripeData.getCurrency().toLowerCase()) // Stripe requiere lowercase
                                .setUnitAmount((long) (item.getUnitPrice() * 100)) // en centavos
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                        .setName(item.getTitle())
                                        .build())
                                .build())
                        .build())
                .collect(Collectors.toList());

        SessionCreateParams params = SessionCreateParams.builder()
                .addAllLineItem(lineItems)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(stripeData.getSuccessUrl())
                .setCancelUrl(stripeData.getCancelUrl())
                .putAllMetadata(stripeData.getMetadata() != null ? stripeData.getMetadata() : java.util.Collections.emptyMap())
                .build();

        try {
            Session session = Session.create(params);
            return new PaymentResponseDTO(session.getId(), session.getUrl());
        } catch (StripeException e) {
            throw new RuntimeException("Error al crear sesión de Stripe: " + e.getMessage(), e);
        }
    }
}
