package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.CourseRequest;
import com.example.studentmanagement.dto.CourseResponse;
import com.example.studentmanagement.exception.BadRequestException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.entity.Course;
import com.example.studentmanagement.repository.CourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Transactional
    public CourseResponse create(CourseRequest request) {
        if (courseRepository.existsByNameIgnoreCase(request.name().trim())) {
            throw new BadRequestException("Course name already exists");
        }
        Course course = new Course();
        apply(course, request);
        return toResponse(courseRepository.save(course));
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> search(String query, String topic) {
        List<Course> courses;
        if (topic != null && !topic.isBlank()) {
            courses = courseRepository.findByTopic(topic);
        } else if (query != null && !query.isBlank()) {
            courses = courseRepository.findByNameContainingIgnoreCase(query);
        } else {
            courses = courseRepository.findAll();
        }
        return courses.stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public Course getCourse(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));
    }

    private void apply(Course course, CourseRequest request) {
        course.setName(request.name().trim());
        course.setDescription(request.description());
        course.setCourseType(request.courseType().trim());
        course.setDuration(request.duration().trim());
        course.setTopics(request.topics().stream().map(String::trim).distinct().toList());
    }

    private CourseResponse toResponse(Course c) {
        return new CourseResponse(c.getId(), c.getName(), c.getDescription(),
                c.getCourseType(), c.getDuration(), c.getTopics());
    }
}
