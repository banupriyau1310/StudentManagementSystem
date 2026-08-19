package com.example.studentmanagement.dto;

import com.example.studentmanagement.entity.Gender;
import java.time.LocalDate;
import java.util.List;

public record StudentResponse(
        Long id,
        String studentCode,
        String name,
        LocalDate dateOfBirth,
        Gender gender,
        String email,
        String mobileNumber,
        String parentsNames,
        List<AddressResponse> addresses,
        List<CourseResponse> courses
) {}
