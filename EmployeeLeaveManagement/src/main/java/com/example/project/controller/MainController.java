package com.example.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/index")
    public String showIndexPage(Model model) {
        // You can add attributes to the model here if needed
        return "index"; // This will render src/main/resources/templates/index.html
    }
}
