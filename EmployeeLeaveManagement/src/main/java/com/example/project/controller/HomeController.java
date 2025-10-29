package com.example.project.controller;

import com.example.project.service.EmployeeService;
import com.example.project.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private LeaveService leaveService;

    // Redirect root to login page instead of index
   // @GetMapping("/")
    //public String redirectToLogin() {
      //  return "redirect:/login";
    //}

    // Show dashboard after successful login
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("employeeCount", employeeService.getAllEmployees().size());
        model.addAttribute("pendingLeavesCount",
                leaveService.getAllLeaves().stream()
                        .filter(l -> "PENDING".equals(l.getStatus()))
                        .count());
        return "index";
    }
}
