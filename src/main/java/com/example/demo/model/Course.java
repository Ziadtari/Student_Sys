package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import javax.persistence.Table;
import jakarta.persistence.*;


@Entity
@Table(name = "courses")  // Optional: adds clarity in DB table naming
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Assuming each course belongs to a user (like a teacher/creator)
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String name;
    private String hours;

    // Default constructor
    public Course() {}

    // Constructor with fields
    public Course(String name, String hours) {
        this.name = name;
        this.hours = hours;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getHours() {
        return hours;
    }
    public void setHours(String hours) {
        this.hours = hours;
    }
}