package com.example.librarycoursework.controller;

import com.example.librarycoursework.exception.ResourceNotFoundException;
import com.example.librarycoursework.model.Loan;
import com.example.librarycoursework.repository.LoanRepository;
import com.example.librarycoursework.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final LoanRepository loanRepository;

    @PostMapping("/issue")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<Loan> issueBook(@RequestParam Long bookId, @RequestParam Long memberId) {
        Loan loan = loanService.issueBook(bookId, memberId);
        return ResponseEntity.ok(loan);
    }

    @PostMapping("/{id}/return")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<Loan> returnBook(@PathVariable Long id) {
        Loan loan = loanService.returnBook(id);
        return ResponseEntity.ok(loan);
    }

    @GetMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long id) {
        Loan loan = loanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + id));
        return ResponseEntity.ok(loan);
    }
}