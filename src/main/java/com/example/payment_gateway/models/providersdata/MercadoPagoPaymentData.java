package com.example.payment_gateway.models.providersdata;

import com.example.payment_gateway.models.dtos.PaymentItemDTO;
import lombok.Data;

import java.util.List;
@Data
public class MercadoPagoPaymentData extends AbstractPaymentData {
    private List<PaymentItemDTO> items;
    private String successUrl;
    private String failureUrl;
    private String pendingUrl;
}
