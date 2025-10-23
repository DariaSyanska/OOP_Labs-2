package com.example.librarysystem.controller;

import com.example.librarysystem.model.Book;
import com.example.librarysystem.model.LibraryMember;
import com.example.librarysystem.repository.BookRepository;
import com.example.librarysystem.repository.LibraryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class LibraryMemberController {

    @Autowired
    private LibraryMemberRepository memberRepository;

    @Autowired
    private BookRepository bookRepository;

    @PostMapping
    public LibraryMember registerMember(@RequestBody LibraryMember member) {
        return memberRepository.save(member);
    }

    @PostMapping("/{memberId}/borrow/{bookId}")
    public ResponseEntity<String> borrowBook(@PathVariable Long memberId, @PathVariable Long bookId) {
        LibraryMember member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found!"));
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found!"));

        member.getBorrowedBooks().add(book);
        memberRepository.save(member);

        return ResponseEntity.ok("Book borrowed successfully!");
    }
}