package com.example.ordermanagement.repository;

import com.example.ordermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Finds a user by their username.
     * @param username the username to search for
     * @return an Optional containing the user if found, or empty otherwise
     */
    Optional<User> findByUsername(String username);
}