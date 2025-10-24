package com.example.librarycoursework.service;

import com.example.librarycoursework.exception.ResourceNotFoundException;
import com.example.librarycoursework.model.*;
import com.example.librarycoursework.repository.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@RequiredArgsConstructor
public class LoanService {
    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;
    private final LibraryMemberRepository memberRepository;
    private final PaymentRepository paymentRepository;
    private static final Logger logger = LoggerFactory.getLogger(LoanService.class);

    public Loan issueBook(Long bookId, Long memberId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + bookId));
        LibraryMember member = memberRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + memberId));

        if (loanRepository.findByBookAndReturnDateIsNull(book).isPresent()) {
            throw new IllegalStateException("Book is already loaned out");
        }

        Loan loan = new Loan();
        loan.setBook(book);
        loan.setMember(member);
        loan.setLoanDate(LocalDate.now());
        loan.setDueDate(LocalDate.now().plusWeeks(2)); // 2 weeks loan period

        logger.info("Issuing book '{}' to member '{}'", book.getTitle(), member.getName());
        return loanRepository.save(loan);
    }

    public Loan returnBook(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() -> new ResourceNotFoundException("Loan not found with id: " + loanId));
        loan.setReturnDate(LocalDate.now());

        if (loan.getReturnDate().isAfter(loan.getDueDate())) {
            long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate(), loan.getReturnDate());
            BigDecimal fineAmount = BigDecimal.valueOf(overdueDays * 0.5); // Assuming a fine of 0.5 per day

            Payment payment = new Payment();
            payment.setAmount(fineAmount);
            payment.setLoan(loan);
            payment.setPaid(false);
            paymentRepository.save(payment);
            loan.setPayment(payment);
            logger.warn("Fine of {} applied for overdue book '{}'", fineAmount, loan.getBook().getTitle());
        }

        logger.info("Book '{}' returned by member '{}'", loan.getBook().getTitle(), loan.getMember().getName());
        return loanRepository.save(loan);
    }
}