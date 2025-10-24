package com.example.librarycoursework.controller;

import com.example.librarycoursework.exception.ResourceNotFoundException;
import com.example.librarycoursework.model.LibraryMember;
import com.example.librarycoursework.model.Loan;
import com.example.librarycoursework.repository.LibraryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class LibraryMemberController {

    private final LibraryMemberRepository memberRepository;

    @GetMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public List<LibraryMember> getAllMembers() {
        return memberRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<LibraryMember> getMemberById(@PathVariable Long id) {
        LibraryMember member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        return ResponseEntity.ok(member);
    }

    @GetMapping("/{id}/loans")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public ResponseEntity<Set<Loan>> getMemberLoans(@PathVariable Long id) {
        LibraryMember member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member not found with id: " + id));
        return ResponseEntity.ok(member.getLoans());
    }
}