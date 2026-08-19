package com.example.studentmanagement.dto;

import com.example.studentmanagement.entity.Gender;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public record CreateStudentRequest(
        @NotBlank String name,
        @NotNull @Past LocalDate dateOfBirth,
        @NotNull Gender gender,
        @NotBlank String studentCode,
        @Valid List<AddressRequest> addresses
) {}
