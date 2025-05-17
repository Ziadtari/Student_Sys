package com.example.demo.controller;


import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import com.example.demo.model.SelectCourse;
import com.example.demo.repository.SelectCourseRepository;
import com.example.demo.model.Attendance;
import com.example.demo.model.User;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class AttendanceController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SelectCourseRepository selectCourseRepository;

    // Show attendance form
    @GetMapping("/mark-attendence")
    public String markAttendanceForm(Model model,HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        List<SelectCourse> selectedCourses = selectCourseRepository.findByUserId(userId);
        List<Long> courseIds = selectedCourses.stream()
                .map(selectCourse -> selectCourse.getCourse().getId())  // Extract course IDs
                .collect(Collectors.toList());

        List<Course> courses = courseRepository.findByIdIn(courseIds);

        model.addAttribute("courses", courses);
        return "attendance/student-mark-attendence"; // Points to templates/attendance/mark.html
    }


    // Admin or teacher views all attendance
    @GetMapping("/view/attendance")
    public String viewAllAttendance(Model model,HttpSession session) {

        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        List<Attendance> attendanceList = attendanceRepository.findAll();

        model.addAttribute("attendanceList", attendanceList);
        return "attendance/view"; // Points to templates/attendance/view.html
    }
}
