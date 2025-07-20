package com.example.payment_gateway.services;

import com.example.payment_gateway.models.dtos.PaymentRequestDTO;
import com.example.payment_gateway.models.dtos.PaymentResponseDTO;
import com.example.payment_gateway.models.providersdata.MercadoPagoPaymentData;
import com.example.payment_gateway.services.processors.PaymentProcessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    private PaymentService paymentService;
    private ObjectMapper objectMapper;
    private PaymentProcessor<MercadoPagoPaymentData> mockProcessor;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();

        mockProcessor = mock(PaymentProcessor.class);
        when(mockProcessor.getProvider()).thenReturn("MERCADO_PAGO");
        when(mockProcessor.getDataClass()).thenReturn(MercadoPagoPaymentData.class);

        paymentService = new PaymentService(List.of(mockProcessor), objectMapper);
    }

    @Test
    void shouldCallCorrectProcessor() {
        PaymentRequestDTO request = new PaymentRequestDTO();
        request.setProvider("MERCADO_PAGO");

        MercadoPagoPaymentData fakeData = new MercadoPagoPaymentData();
        request.setProviderData(fakeData);

        PaymentResponseDTO expectedResponse = new PaymentResponseDTO("pref_123", "https://init.point.url");
        when(mockProcessor.initiatePayment(any())).thenReturn(expectedResponse);

        PaymentResponseDTO actualResponse = paymentService.createPayment(request);

        assertEquals(expectedResponse, actualResponse);
        verify(mockProcessor, times(1)).initiatePayment(any());
    }

    @Test
    void shouldThrowExceptionWhenProviderNotSupported() {
        PaymentRequestDTO request = new PaymentRequestDTO();
        request.setProvider("UNKNOWN_PROVIDER");
        request.setProviderData(new MercadoPagoPaymentData());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            paymentService.createPayment(request);
        });

        assertTrue(exception.getMessage().contains("Proveedor no soportado"));
    }
}
