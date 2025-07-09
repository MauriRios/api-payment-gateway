package com.example.payment_gateway.processors;

import com.example.payment_gateway.models.dtos.PaymentResponseDTO;
import com.example.payment_gateway.models.providersdata.AbstractPaymentData;

public interface PaymentProcessor {
    String getProvider(); // ejemplo: "MERCADO_PAGO"
    PaymentResponseDTO initiatePayment(AbstractPaymentData data);
}
