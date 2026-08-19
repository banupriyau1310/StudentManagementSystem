package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.*;
import com.example.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/profile")
    public StudentResponse profile(Authentication authentication) {
        return studentService.getProfile(studentId(authentication));
    }

    @PutMapping("/profile")
    public StudentResponse updateProfile(Authentication authentication,
                                         @Valid @RequestBody UpdateStudentProfileRequest request) {
        return studentService.updateProfile(studentId(authentication), request);
    }

    @GetMapping("/courses")
    public List<CourseResponse> getCourses(Authentication authentication,
                                           @RequestParam(required = false) String topic) {
        return studentService.getAssignedCourses(studentId(authentication), topic);
    }

    @DeleteMapping("/courses/{courseId}")
    public MessageResponse leaveCourse(Authentication authentication, @PathVariable Long courseId) {
        return studentService.leaveCourse(studentId(authentication), courseId);
    }

    private Long studentId(Authentication authentication) {
        return Long.valueOf(authentication.getDetails().toString());
    }
}
