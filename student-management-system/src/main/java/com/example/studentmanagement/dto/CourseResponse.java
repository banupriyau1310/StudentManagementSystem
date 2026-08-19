package com.example.studentmanagement.dto;

import java.util.List;

public record CourseResponse(
        Long id,
        String name,
        String description,
        String courseType,
        String duration,
        List<String> topics
) {}
