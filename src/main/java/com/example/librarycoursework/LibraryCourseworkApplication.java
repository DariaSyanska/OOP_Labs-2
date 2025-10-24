package com.example.librarycoursework;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.librarycoursework")
@EntityScan(basePackages = "com.example.librarycoursework.model")
public class LibraryCourseworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryCourseworkApplication.class, args);
    }

}