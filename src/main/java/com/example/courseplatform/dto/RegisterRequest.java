package com.example.courseplatform.dto;

import com.example.courseplatform.model.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
    private String name; // Ім'я для Студента чи Викладача
}