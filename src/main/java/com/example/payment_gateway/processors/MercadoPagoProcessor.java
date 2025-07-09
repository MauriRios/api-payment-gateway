package com.example.payment_gateway.processors;

import com.example.payment_gateway.models.dtos.PaymentResponseDTO;
import com.example.payment_gateway.models.providersdata.AbstractPaymentData;
import com.example.payment_gateway.models.providersdata.MercadoPagoPaymentData;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MercadoPagoProcessor implements PaymentProcessor {

    @Override
    public String getProvider() {
        return "MERCADO_PAGO";
    }
    @Override
    public PaymentResponseDTO initiatePayment(AbstractPaymentData data) {
        MercadoPagoPaymentData mpData = (MercadoPagoPaymentData) data;

        List<PreferenceItemRequest> items = mpData.getItems().stream()
                .map(i -> PreferenceItemRequest.builder()
                        .title(i.getTitle())
                        .quantity(i.getQuantity())
                        .unitPrice(new BigDecimal(i.getUnitPrice()))
                        .build())
                .toList();

        PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                .success(mpData.getSuccessUrl())
                .failure(mpData.getFailureUrl())
                .pending(mpData.getPendingUrl())
                .build();

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .items(items)
                .backUrls(backUrls)
                .autoReturn("approved")
                .build();

        try {
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            return new PaymentResponseDTO(preference.getId(), preference.getInitPoint());
        } catch (MPApiException e) {
            throw new RuntimeException("Mercado Pago error: " + e.getApiResponse().getContent());
        } catch (MPException e) {
            throw new RuntimeException("Error al crear preferencia", e);
        }
    }
}

