package com.example.librarycoursework.repository;

import com.example.librarycoursework.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuthorRepository extends JpaRepository<Author, Long> {}