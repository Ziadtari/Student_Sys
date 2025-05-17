package com.example.demo.controller;

import com.example.demo.model.Attendance;
import com.example.demo.model.User;
import com.example.demo.repository.AttendanceRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import com.example.demo.model.SelectCourse;
import com.example.demo.repository.SelectCourseRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import com.example.demo.dto.AttendanceRequest;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceApiController {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CourseRepository courseRepository;

    // ✅ Admin can view all attendance records
    @GetMapping
    public List<Attendance> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    // ✅ Student marks attendance (only once per day)
    @PostMapping("/attendance/mark")
    public ResponseEntity<String> markAttendance(@RequestBody AttendanceRequest request, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return new ResponseEntity<>("User not authenticated", HttpStatus.UNAUTHORIZED);
        }

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>("User not found", HttpStatus.UNAUTHORIZED);
        }

        Optional<Course> courseOptional = courseRepository.findById(request.getCourseId());
        if (courseOptional.isEmpty()) {
            return new ResponseEntity<>("Course not found", HttpStatus.BAD_REQUEST);
        }

        User user = userOptional.get();
        Course course = courseOptional.get();

        // Check if already marked for today
        LocalDate today = LocalDate.now();
        List<Attendance> todaysAttendance = attendanceRepository.findByUserAndDate(user, today);

        if (!todaysAttendance.isEmpty()) {
            return new ResponseEntity<>("Attendance already marked for today", HttpStatus.BAD_REQUEST);
        }

        Attendance attendance = new Attendance();
        attendance.setUser(user);
        attendance.setCourse(course);
        attendance.setDate(today);
        attendance.setStatus(request.getStatus());

        attendanceRepository.save(attendance);

        return new ResponseEntity<>("Attendance marked successfully", HttpStatus.CREATED);
    }

    // ✅ Admin gets attendance for a specific student
    @GetMapping("/student/{userId}")
    public ResponseEntity<List<Attendance>> getAttendanceByStudent(@PathVariable Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Attendance> attendanceList = attendanceRepository.findByUser(userOptional.get());
        return new ResponseEntity<>(attendanceList, HttpStatus.OK);
    }
}
