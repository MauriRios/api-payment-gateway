package com.example.payment_gateway.controllers;


import com.example.payment_gateway.models.dtos.PaymentRequestDTO;
import com.example.payment_gateway.models.dtos.PaymentResponseDTO;
import com.example.payment_gateway.services.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate")
    public ResponseEntity<PaymentResponseDTO> initiatePayment(@RequestBody PaymentRequestDTO request) {
        logger.info("Recibido PaymentRequest completo: {}", request);
        return ResponseEntity.ok(paymentService.createPayment(request));
    }
}
