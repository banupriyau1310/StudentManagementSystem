package com.example.studentmanagement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import java.util.List;

public record UpdateStudentProfileRequest(
        @Email String email,
        @Size(max = 20) String mobileNumber,
        @Size(max = 200) String parentsNames,
        @Valid List<AddressRequest> addresses
) {}
