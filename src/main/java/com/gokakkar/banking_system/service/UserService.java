package com.gokakkar.banking_system.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import com.gokakkar.banking_system.model.User;
import com.gokakkar.banking_system.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User registerUser(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepo.save(user);
    }

    public User getUserByEmail(String email)
    {
        return userRepo.findByEmail(email);
    }

    // Check if a user exists by email
    public boolean isUserExists(String email)
    {
        return userRepo.existsByEmail(email);
    }
}


