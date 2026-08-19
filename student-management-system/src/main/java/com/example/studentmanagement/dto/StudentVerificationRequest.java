package com.example.studentmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record StudentVerificationRequest(
        @NotBlank String studentCode,
        @NotNull LocalDate dateOfBirth
) {}
