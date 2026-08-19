package com.example.studentmanagement.dto;

import jakarta.validation.constraints.*;
import java.util.List;

public record CourseRequest(
        @NotBlank String name,
        @Size(max = 1000) String description,
        @NotBlank String courseType,
        @NotBlank String duration,
        @NotEmpty List<@NotBlank String> topics
) {}
