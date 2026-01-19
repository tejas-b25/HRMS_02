package com.example.User.config;

import com.example.User.entity.Role;
import com.example.User.entity.User;
import com.example.User.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SuperAdminInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (!userRepository.existsByRole(Role.SUPERADMIN)) {
            User superAdmin = User.builder()
                    .username("superadmin")
                    .password(passwordEncoder.encode("superadmin123"))
                    .email("superadmin@example.com")
                    .firstName("System")
                    .lastName("SuperAdmin")
                    .role(Role.SUPERADMIN)
                    .status("ACTIVE")
                    .build();
            userRepository.save(superAdmin);
            System.out.println("SUPERADMIN created");
        }
    }

}
