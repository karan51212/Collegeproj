package com.example.project.controller;

import com.example.project.model.Leave;
import com.example.project.service.EmployeeService;
import com.example.project.service.LeaveService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/leaves")
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String getAllLeaves(@RequestParam(required = false) String keyword, Model model) {
        List<Leave> leaves;
        if (keyword != null && !keyword.isEmpty()) {
            leaves = leaveService.searchLeaves(keyword);
        } else {
            leaves = leaveService.getAllLeaves();
        }
        model.addAttribute("leaves", leaves);
        model.addAttribute("keyword", keyword);
        return "leaves";
    }

    @GetMapping("/new")
    public String createLeaveForm(Model model) {
        model.addAttribute("leave", new Leave());
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "create_leave";
    }

    @PostMapping
    public String saveLeave(@Valid @ModelAttribute("leave") Leave leave, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeService.getAllEmployees());
            return "create_leave";
        }
        leaveService.saveLeave(leave);
        return "redirect:/leaves";
    }

    @GetMapping("/edit/{id}")
    public String editLeaveForm(@PathVariable Long id, Model model) {
        model.addAttribute("leave", leaveService.getLeaveById(id));
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "edit_leave";
    }

    @PostMapping("/{id}")
    public String updateLeave(@PathVariable Long id, @Valid @ModelAttribute("leave") Leave leave, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("employees", employeeService.getAllEmployees());
            return "edit_leave";
        }
        Leave existingLeave = leaveService.getLeaveById(id);
        existingLeave.setEmployee(leave.getEmployee());
        existingLeave.setStartDate(leave.getStartDate());
        existingLeave.setEndDate(leave.getEndDate());
        existingLeave.setReason(leave.getReason());
        existingLeave.setStatus(leave.getStatus());
        leaveService.saveLeave(existingLeave);
        return "redirect:/leaves";
    }

    @GetMapping("/delete/{id}")
    public String deleteLeave(@PathVariable Long id) {
        leaveService.deleteLeave(id);
        return "redirect:/leaves";
    }
}
