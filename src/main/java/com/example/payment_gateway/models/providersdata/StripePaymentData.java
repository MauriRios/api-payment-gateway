package com.example.payment_gateway.models.providersdata;

import com.example.payment_gateway.models.dtos.PaymentItemDTO;
import lombok.Data;

import java.util.List;
import java.util.Map;
@Data
public class StripePaymentData extends AbstractPaymentData {
    private List<PaymentItemDTO> items;
    private String successUrl;
    private String cancelUrl;
    private String currency;
    private Map<String, String> metadata;
}
