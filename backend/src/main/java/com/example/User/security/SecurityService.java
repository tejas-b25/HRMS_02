package com.example.User.security;

import com.example.User.entity.Role;
import com.example.User.entity.User;
import org.springframework.stereotype.Service;

@Service("securityService")
public class SecurityService {

    public boolean canAccessEmployee(User user, Long employeeId) {
        if (user.getRole() != Role.EMPLOYEE) {
            return false;
        }
        return user.getEmployee() != null
                && user.getEmployee().getId().equals(employeeId);
    }

    public boolean canAccessHr(User user) {
        return user.getRole() == Role.HR
                || user.getRole() == Role.ADMIN
                || user.getRole() == Role.MANAGER;
    }
}
