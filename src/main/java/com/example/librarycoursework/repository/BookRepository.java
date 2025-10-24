package com.example.librarycoursework.repository;

import com.example.librarycoursework.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
public interface BookRepository extends JpaRepository<Book, Long> {}