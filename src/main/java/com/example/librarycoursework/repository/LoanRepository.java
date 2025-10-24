package com.example.librarycoursework.repository;

import com.example.librarycoursework.model.Book;
import com.example.librarycoursework.model.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface LoanRepository extends JpaRepository<Loan, Long> {
    Optional<Loan> findByBookAndReturnDateIsNull(Book book);
}