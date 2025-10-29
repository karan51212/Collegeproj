package com.example.project.controller;

import com.example.project.model.User;
import com.example.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    // Redirect root URL to login
    @GetMapping("/")
    public String redirectToLogin() {
        return "redirect:/login";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@ModelAttribute User user, Model model) {
        userService.register(user);
        model.addAttribute("email", user.getEmail());
        return "verifyOtp";
    }

    @PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam String email, @RequestParam String otp, Model model) {
        boolean verified = userService.verifyOtp(email, otp);
        if (verified) {
            model.addAttribute("message", "Account verified! Please log in.");
            return "login";
        } else {
            model.addAttribute("error", "Invalid OTP. Try again.");
            model.addAttribute("email", email);
            return "verifyOtp";
        }
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String email, @RequestParam String password, Model model) {
        User user = userService.login(email, password);
        if (user != null) {
            model.addAttribute("user", user);
            return "index";
        } else {
            model.addAttribute("error", "Invalid credentials or email not verified.");
            return "login";
        }
    }
}
