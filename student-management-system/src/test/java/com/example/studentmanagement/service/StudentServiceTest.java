package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.CreateStudentRequest;
import com.example.studentmanagement.entity.Gender;
import com.example.studentmanagement.repository.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {
    @Mock StudentRepository studentRepository;
    @Mock CourseRepository courseRepository;
    @Mock EnrollmentRepository enrollmentRepository;

    @InjectMocks StudentService studentService;

    @Test
    void shouldCreateStudentWithUniqueCode() {
        CreateStudentRequest request = new CreateStudentRequest(
                "Test Student", LocalDate.of(2000, 1, 1), Gender.OTHER, "STU9001", List.of());

        when(studentRepository.existsByStudentCode("STU9001")).thenReturn(false);
        when(studentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        var result = studentService.create(request);

        assertEquals("STU9001", result.studentCode());
        assertEquals("Test Student", result.name());
        verify(studentRepository).save(any());
    }
}
