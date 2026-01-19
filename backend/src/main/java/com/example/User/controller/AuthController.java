package com.example.User.controller;

import com.example.User.dto.JwtResponse;
import com.example.User.entity.User;
import com.example.User.security.JwtUtil;
import com.example.User.service.AuditLogService;
import com.example.User.service.EmployeeService;
import com.example.User.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody User user) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );
        UserDetails details = (UserDetails) auth.getPrincipal();
        String role = details.getAuthorities().iterator().next().getAuthority();

        auditLogService.logEvent(user.getUsername(), "LOGIN");


        String token = jwtUtil.generateToken(details.getUsername(), role);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    @PostMapping("/admin")
    public ResponseEntity<User> createAdmin(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerAdmin(user));
    }


    @PostMapping("/user")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerUser(user));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }


    @PutMapping("/admin/user/{id}/activate")
    public ResponseEntity<User> activateUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.activateUser(id));
    }

    @PutMapping("/admin/user/{id}/deactivate")
    public ResponseEntity<User> deactivateUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.deactivateUser(id));
    }

    //Implement Audit
    //editing by rohit
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        String username = jwtUtil.extractUsernameFromRequest(request);
        userService.logout(username);
        return ResponseEntity.ok("Logged out successfully");
    }


    //  Admin create HR
    @PostMapping("/hr")
    public ResponseEntity<User> createHr(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerHr(user));
    }

    //  HR create Manager
    @PostMapping("/manager")
    public ResponseEntity<User> createManager(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerManager(user));
    }

    //  Manager create Employee
    @PostMapping("/employee")
    public ResponseEntity<User> createEmployee(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerEmployee(user));
    }

    //  Finance create Finance User
    @PostMapping("/finance")
    public ResponseEntity<User> createFinance(@RequestBody User user) {
        return ResponseEntity.ok(userService.registerFinance(user));
    }


}
