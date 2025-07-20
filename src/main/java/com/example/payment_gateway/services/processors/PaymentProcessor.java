package com.example.payment_gateway.services.processors;

import com.example.payment_gateway.models.dtos.PaymentResponseDTO;
import com.example.payment_gateway.models.providersdata.AbstractPaymentData;

public interface PaymentProcessor<T extends AbstractPaymentData> {
    String getProvider(); // Devuelve el proveedor, ejemplo: "MERCADO_PAGO"
    Class<T> getDataClass(); // Devuelve el la clase del proccesor que se ejecutara
    PaymentResponseDTO initiatePayment(AbstractPaymentData data); // inicia el servicio de pago
}
