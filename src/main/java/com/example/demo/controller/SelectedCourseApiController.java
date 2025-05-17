package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.security.core.context.SecurityContextHolder;
import com.example.demo.model.User;
import org.springframework.security.core.Authentication;
import com.example.demo.repository.UserRepository;
import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import com.example.demo.model.SelectCourse;
import com.example.demo.repository.SelectCourseRepository;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/course/select")
public class SelectedCourseApiController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SelectCourseRepository selectCourseRepository;

    //    API's
    @GetMapping
    public List<Course> getAllCourse() {
        return courseRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<SelectCourse> createSelectCourse(@RequestBody Map<String, Object> payload, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        System.out.println("User ID from session: " + userId);
        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        User user = userOptional.get();

        Object courseIdObj = payload.get("courseId");
        if (courseIdObj == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Long courseId;
        try {
            courseId = Long.valueOf(courseIdObj.toString());
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Course> courseOptional = courseRepository.findById(courseId);
        if (courseOptional.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Course course = courseOptional.get();

        SelectCourse selectcourse = new SelectCourse();

        selectcourse.setUser(user);
        selectcourse.setCourse(course);
        SelectCourse saved = selectCourseRepository.save(selectcourse);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getcourseById(@PathVariable Long id) {
        Optional<Course> course = courseRepository.findById(id);

        if (course.isPresent()) {
            return new ResponseEntity<>(course.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Course> updateCourse(@PathVariable Long id, @RequestBody Course updatedCourse) {
        Optional<Course> courseOptional = courseRepository.findById(id);

        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();

            course.setName(updatedCourse.getName());
//            course.setDescription(updatedCourse.getDescription());

            courseRepository.save(course);

            return new ResponseEntity<>(course, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseRepository.deleteById(id);
    }
//    END API's
}