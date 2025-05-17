package com.example.demo.service;

import com.example.demo.repository.UserRepository;
import com.example.demo.model.User;
import com.example.demo.repository.SelectCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.SelectCourse;

import java.util.List;

@Service
public class SelectCourseService {


    @Autowired
    private SelectCourseRepository selectCourseRepository;

    @Autowired
    private UserRepository userRepository;

    public SelectCourse selectCourses(SelectCourse selectcourse) {
        return selectCourseRepository.save(selectcourse);
    }

    public List<SelectCourse> getAllSelectCourse() {
        return selectCourseRepository.findAll();
    }

    public List<SelectCourse> findByUserId(User user) {
        return selectCourseRepository.findByUser(user);
    }
}
