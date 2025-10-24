package com.example.ordermanagement.dto;

import com.example.ordermanagement.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
}