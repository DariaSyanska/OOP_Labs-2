package com.example.librarycoursework.dto;
import lombok.Data;
@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String roleName; // e.g., "LIBRARIAN" or "MEMBER"
    private String memberName; // Name for the LibraryMember profile
}