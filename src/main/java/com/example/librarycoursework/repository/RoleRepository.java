package com.example.librarycoursework.repository;

import com.example.librarycoursework.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(String name);
}