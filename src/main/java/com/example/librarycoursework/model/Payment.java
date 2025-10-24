package com.example.librarycoursework.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private boolean isPaid = false;

    @OneToOne
    @JoinColumn(name = "loan_id")
    @JsonIgnore
    private Loan loan;
}