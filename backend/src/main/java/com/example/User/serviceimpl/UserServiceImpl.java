package com.example.User.serviceimpl;
import com.example.User.entity.Role;
import com.example.User.entity.User;
import com.example.User.exception.UserNotFoundException;
import com.example.User.repository.UserRepository;
import com.example.User.service.AuditLogService;
import com.example.User.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuditLogService auditLogService;

    @Override
    public User registerAdmin(User user) {
        String currentUsername = getCurrentUsername();
        User creator = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        if (creator.getRole() != Role.SUPERADMIN) {
            throw new RuntimeException("Only SUPERADMIN can create ADMIN");
        }

        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User registerUser(User user) {
        String currentUsername = getCurrentUsername();
        User creator = userRepository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("Creator not found"));

        if (creator.getRole() == Role.USER) {
            throw new RuntimeException("USER cannot create accounts");
        }

        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        auditLogService.logEvent(user.getUsername(), "LOGIN");

        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }

    public User activateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus("ACTIVE");
        return userRepository.save(user);
    }

    public User deactivateUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setStatus("INACTIVE");
        return userRepository.save(user);
    }


@Override
public void logout(String username) {
    auditLogService.logEvent(username, "LOGOUT");
}

    // Register HR
    public User registerHr(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.HR);
        return userRepository.save(user);
    }

    // Register Manager
    public User registerManager(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.MANAGER);
        return userRepository.save(user);
    }

    // Register Employee
    public User registerEmployee(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.EMPLOYEE);
        return userRepository.save(user);
    }

    // Register Finance
    public User registerFinance(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.FINANCE);
        return userRepository.save(user);
    }


}
