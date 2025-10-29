// service/UserService.java
package com.example.project.service;

import com.example.project.model.User;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmailService emailService;

    public User register(User user) {
        String otp = String.valueOf(100000 + new Random().nextInt(900000));
        user.setOtp(otp);
        user.setVerified(false);
        userRepository.save(user);

        emailService.sendOtp(user.getEmail(), otp);
        return user;
    }

    public boolean verifyOtp(String email, String enteredOtp) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.getOtp().equals(enteredOtp)) {
            user.setVerified(true);
            user.setOtp(null);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && user.isVerified() && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
