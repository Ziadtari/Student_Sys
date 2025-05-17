// src/main/java/com/example/demo/dto/AttendanceRequest.java
package com.example.demo.dto;

public class AttendanceRequest {
    private Long courseId;
    private String status;

    // Getters and Setters
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
