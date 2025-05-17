package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.service.UserService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.demo.model.User;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;
import java.time.LocalDate;
import java.util.ArrayList;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/dashboard")
    public String showAdminDashboard(Model model) {
        return "admindashboard";
    }

    @GetMapping("/student/dashboard")
    public String showStudentDashboard(Model model) {
        return "studentdashboard";
    }


}