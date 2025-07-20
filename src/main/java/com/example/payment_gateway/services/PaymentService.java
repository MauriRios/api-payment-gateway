package com.example.payment_gateway.services;

import com.example.payment_gateway.models.dtos.PaymentRequestDTO;
import com.example.payment_gateway.models.dtos.PaymentResponseDTO;
import com.example.payment_gateway.models.providersdata.AbstractPaymentData;
import com.example.payment_gateway.services.processors.PaymentProcessor;
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

        PaymentProcessor<?> processor = processors.get(providerKey);
        if (processor == null) {
            throw new IllegalArgumentException("Proveedor no soportado: " + providerKey);
        }

        AbstractPaymentData data = objectMapper.convertValue(
                request.getProviderData(),
                processor.getDataClass()
        );

        return processor.initiatePayment(data);
    }
}
