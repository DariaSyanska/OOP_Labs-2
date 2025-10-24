package com.example.librarycoursework.repository;

import com.example.librarycoursework.model.LibraryMember;
import org.springframework.data.jpa.repository.JpaRepository;
public interface LibraryMemberRepository extends JpaRepository<LibraryMember, Long> {}