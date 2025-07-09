package com.example.payment_gateway.models.dtos;

import lombok.Data;

@Data
public class PaymentItemDTO {
    private String title;
    private int quantity;
    private float unitPrice;
}
