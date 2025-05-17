package com.example.demo.controller;

import com.example.demo.model.Course;
import com.example.demo.repository.CourseRepository;
import com.example.demo.model.SelectCourse;
import com.example.demo.repository.SelectCourseRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/course")
public class CourseController {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SelectCourseRepository selectCourseRepository;

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("course", new Course());
        return "course/add";
    }


    @GetMapping("/show")
    public String getAllCourse (Model model) {
        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "course/show";
    }



    @GetMapping("/select/course")
    public String getAllCourses (Model model) {

        List<Course> courses = courseRepository.findAll();
        model.addAttribute("courses", courses);
        return "course/selectedcourse";
    }

    @GetMapping("/select/course/view")
    public String showAllSelectCourses (Model model, HttpSession session) {
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
        return "course/studentCourseSelectView";
    }



    @GetMapping("/edit/{id}")
    public String editCourse(@PathVariable Long id, Model model) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            model.addAttribute("course", optionalCourse.get());
            return "Course/edit";
        } else {

            return "redirect:/course/show?error=CourseNotFound";
        }
    }
}

