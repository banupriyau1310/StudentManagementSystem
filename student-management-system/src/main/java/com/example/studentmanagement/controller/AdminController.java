package com.example.studentmanagement.controller;

import com.example.studentmanagement.dto.*;
import com.example.studentmanagement.service.CourseService;
import com.example.studentmanagement.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final StudentService studentService;
    private final CourseService courseService;

    public AdminController(StudentService studentService, CourseService courseService) {
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @PostMapping("/createStudent")
    public StudentResponse createStudent(@Valid @RequestBody CreateStudentRequest request) {
        return studentService.create(request);
    }

    @PostMapping("/createCourse")
    public CourseResponse createCourse(@Valid @RequestBody CourseRequest request) {
        return courseService.create(request);
    }

    @PostMapping("/students/{studentId}/courses/{courseId}")
    public MessageResponse assignCourse(@PathVariable Long studentId, @PathVariable Long courseId) {
        return studentService.assignCourse(studentId, courseId);
    }

    @GetMapping("/students")
    public List<StudentResponse> searchStudents(@RequestParam String name) {
        return studentService.searchByName(name);
    }

    @GetMapping("/courses/{courseId}/students")
    public List<StudentResponse> getStudentsByCourse(@PathVariable Long courseId) {
        return studentService.findByCourse(courseId);
    }

    @GetMapping("/courses")
    public List<CourseResponse> searchCourses(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) String topic) {
        return courseService.search(q, topic);
    }
}
