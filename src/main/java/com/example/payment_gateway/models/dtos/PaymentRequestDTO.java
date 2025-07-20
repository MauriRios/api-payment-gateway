package com.example.payment_gateway.models.dtos;

import com.example.payment_gateway.models.providersdata.AbstractPaymentData;
import com.example.payment_gateway.models.providersdata.MercadoPagoPaymentData;
import com.example.payment_gateway.models.providersdata.StripePaymentData;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;


@Data
public class PaymentRequestDTO {
    private String provider;

    @JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "provider")
    @JsonSubTypes({
            @JsonSubTypes.Type(value = MercadoPagoPaymentData.class, name = "MERCADO_PAGO"),
            @JsonSubTypes.Type(value = StripePaymentData.class, name = "STRIPE")
    })
    private AbstractPaymentData providerData;
}
