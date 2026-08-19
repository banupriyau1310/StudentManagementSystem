package com.example.studentmanagement.dto;

public record AuthResponse(String token, String role, Long studentId) {}
