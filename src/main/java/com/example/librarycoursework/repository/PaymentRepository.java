package com.example.librarycoursework.repository;

import com.example.librarycoursework.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PaymentRepository extends JpaRepository<Payment, Long> {}