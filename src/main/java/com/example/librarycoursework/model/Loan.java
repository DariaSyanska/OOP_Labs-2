package com.example.librarycoursework.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Entity
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private LibraryMember member;

    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;

    @OneToOne(mappedBy = "loan", cascade = CascadeType.ALL)
    private Payment payment;
}