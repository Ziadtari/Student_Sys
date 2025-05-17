package com.example.demo.repository;

import com.example.demo.model.SelectCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.example.demo.model.User;

public interface SelectCourseRepository extends JpaRepository<SelectCourse, Long> {
    List<SelectCourse> findByUserId(Long id);
    List<SelectCourse> findByUser(User user);
}