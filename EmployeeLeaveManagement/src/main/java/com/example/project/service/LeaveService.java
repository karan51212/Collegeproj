package com.example.project.service;

import com.example.project.model.Leave;
import com.example.project.repository.LeaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveService {
    @Autowired
    private LeaveRepository leaveRepository;

    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public Leave getLeaveById(Long id) {
        return leaveRepository.findById(id).orElse(null);
    }

    public void saveLeave(Leave leave) {
        leaveRepository.save(leave);
    }

    public void deleteLeave(Long id) {
        leaveRepository.deleteById(id);
    }

    public List<Leave> searchLeaves(String keyword) {
        return leaveRepository.findByEmployeeNameContainingIgnoreCaseOrStatusContainingIgnoreCase(keyword, keyword);
    }
}
