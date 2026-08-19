package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.*;
import com.example.studentmanagement.entity.AdminUser;
import com.example.studentmanagement.entity.Student;
import com.example.studentmanagement.exception.BadRequestException;
import com.example.studentmanagement.repository.AdminUserRepository;
import com.example.studentmanagement.repository.StudentRepository;
import com.example.studentmanagement.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AdminUserRepository adminUserRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(AdminUserRepository adminUserRepository, StudentRepository studentRepository,
                       PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.adminUserRepository = adminUserRepository;
        this.studentRepository = studentRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponse adminLogin(LoginRequest request) {
        AdminUser admin = adminUserRepository.findByUsername(request.username())
                .orElseThrow(() -> new BadRequestException("Invalid username or password"));

        if (!passwordEncoder.matches(request.password(), admin.getPassword())) {
            throw new BadRequestException("Invalid username or password");
        }

        String token = jwtService.generateToken(admin.getUsername(), admin.getRole(), admin.getId());
        return new AuthResponse(token, admin.getRole(), null);
    }

    public AuthResponse studentVerify(StudentVerificationRequest request) {
        Student student = studentRepository.findByStudentCodeAndDateOfBirth(
                request.studentCode(), request.dateOfBirth())
                .orElseThrow(() -> new BadRequestException("Invalid student code or date of birth"));

        String token = jwtService.generateToken(student.getStudentCode(), "STUDENT", student.getId());
        return new AuthResponse(token, "STUDENT", student.getId());
    }
}
