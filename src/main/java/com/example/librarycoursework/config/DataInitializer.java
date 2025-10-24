package com.example.librarycoursework.config;

import com.example.librarycoursework.model.Role;
import com.example.librarycoursework.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (roleRepository.findByName("LIBRARIAN") == null) {
            Role librarianRole = new Role();
            librarianRole.setName("LIBRARIAN");
            roleRepository.save(librarianRole);
        }
        if (roleRepository.findByName("MEMBER") == null) {
            Role memberRole = new Role();
            memberRole.setName("MEMBER");
            roleRepository.save(memberRole);
        }
    }
}