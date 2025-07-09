package com.example.payment_gateway.services;

import com.example.payment_gateway.models.dtos.PaymentRequestDTO;
import com.example.payment_gateway.models.dtos.PaymentResponseDTO;
import com.example.payment_gateway.models.providersdata.AbstractPaymentData;
import com.example.payment_gateway.models.providersdata.MercadoPagoPaymentData;
import com.example.payment_gateway.models.providersdata.StripePaymentData;
import com.example.payment_gateway.processors.PaymentProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    private final Map<String, PaymentProcessor> processors;
    private final ObjectMapper objectMapper;

    public PaymentService(List<PaymentProcessor> processorList, ObjectMapper objectMapper) {
        this.processors = processorList.stream()
                .collect(Collectors.toMap(PaymentProcessor::getProvider, Function.identity()));
        this.objectMapper = objectMapper;
    }

    public PaymentResponseDTO createPayment(PaymentRequestDTO request) {
        String providerKey = request.getProvider().toUpperCase();

        PaymentProcessor processor = processors.get(providerKey);
        if (processor == null) {
            throw new IllegalArgumentException("Proveedor de pago no soportado: " + providerKey);
        }

        Class<? extends AbstractPaymentData> dataClass = switch (providerKey) {
            case "MERCADO_PAGO" -> MercadoPagoPaymentData.class;
            case "STRIPE" -> StripePaymentData.class;
            default -> throw new IllegalArgumentException("Clase no definida para: " + providerKey);
        };

        AbstractPaymentData data = objectMapper.convertValue(request.getProviderData(), dataClass);

        return processor.initiatePayment(data);
    }
}
