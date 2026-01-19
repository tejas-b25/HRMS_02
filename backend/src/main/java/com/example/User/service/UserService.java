package com.example.User.service;

import com.example.User.entity.User;

import java.util.List;

public interface UserService {
    User registerAdmin(User user);
    User registerUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();


    User activateUser(Long id);
    User deactivateUser(Long id);

    User registerHr(User user);
    User registerManager(User user);
    User registerEmployee(User user);
    User registerFinance(User user);
    //edit by rohit
    void logout(String username);

}
