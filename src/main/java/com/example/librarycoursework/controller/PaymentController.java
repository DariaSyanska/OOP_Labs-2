package com.example.librarycoursework.controller;

import com.example.librarycoursework.exception.ResourceNotFoundException;
import com.example.librarycoursework.model.Payment;
import com.example.librarycoursework.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentRepository paymentRepository;

    @PostMapping("/{id}/pay")
    @PreAuthorize("hasRole('MEMBER') or hasRole('LIBRARIAN')")
    public ResponseEntity<Payment> payFine(@PathVariable Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Payment not found with id: " + id));

        if (payment.isPaid()) {
            throw new IllegalStateException("This fine has already been paid.");
        }

        payment.setPaid(true);
        payment.setPaymentDate(LocalDate.now());
        Payment updatedPayment = paymentRepository.save(payment);
        return ResponseEntity.ok(updatedPayment);
    }

    @GetMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public java.util.List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}