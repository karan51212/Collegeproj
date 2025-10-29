package com.example.project.repository;

import com.example.project.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByEmployeeNameContainingIgnoreCaseOrStatusContainingIgnoreCase(String employeeName, String status);
}
