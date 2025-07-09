package com.example.payment_gateway.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentResponseDTO {
    private String preferenceId;
    private String initPoint;
}
