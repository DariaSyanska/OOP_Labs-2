package com.example.courseplatform.controller;

import com.example.courseplatform.dto.AuthRequest;
import com.example.courseplatform.dto.AuthResponse;
import com.example.courseplatform.dto.RegisterRequest;
import com.example.courseplatform.model.Instructor;
import com.example.courseplatform.model.Role;
import com.example.courseplatform.model.Student;
import com.example.courseplatform.model.User;
import com.example.courseplatform.repository.InstructorRepository;
import com.example.courseplatform.repository.StudentRepository;
import com.example.courseplatform.repository.UserRepository;
import com.example.courseplatform.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final InstructorRepository instructorRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username is already taken!");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());

        if (request.getRole() == Role.STUDENT) {
            Student student = new Student();
            student.setName(request.getName());
            student.setUser(user);
            studentRepository.save(student); // Cascade will save the user as well
        } else if (request.getRole() == Role.INSTRUCTOR) {
            Instructor instructor = new Instructor();
            instructor.setName(request.getName());
            // Note: Instructor doesn't have a direct user link in this model
            // We save the instructor and then the user separately.
            instructorRepository.save(instructor);
            userRepository.save(user); // Save the user separately for instructor
        } else {
            return ResponseEntity.badRequest().body("Invalid role specified!");
        }

        return ResponseEntity.ok("User registered successfully!");
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        var user = userRepository.findByUsername(request.getUsername()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }
}