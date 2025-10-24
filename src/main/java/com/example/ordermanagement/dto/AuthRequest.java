package com.example.ordermanagement.dto;

import lombok.Data;

@Data
public class AuthRequest {
    private String username;
    private String password;
}