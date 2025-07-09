package com.example.payment_gateway.controllers;


import com.example.payment_gateway.models.dtos.PaymentRequestDTO;
import com.example.payment_gateway.models.dtos.PaymentResponseDTO;
import com.example.payment_gateway.services.PaymentService;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponseDTO> initiatePayment(@RequestBody PaymentRequestDTO request) throws MPException, MPApiException {
        log.info("Recibido PaymentRequest completo: {}", request);
        return ResponseEntity.ok(paymentService.createPayment(request));
    }
}
