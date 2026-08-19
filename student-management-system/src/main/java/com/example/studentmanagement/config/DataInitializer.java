package com.example.studentmanagement.config;

import com.example.studentmanagement.entity.*;
import com.example.studentmanagement.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner seedData(AdminUserRepository adminRepo, StudentRepository studentRepo,
                                CourseRepository courseRepo, EnrollmentRepository enrollmentRepo,
                                PasswordEncoder encoder) {
        return args -> {
            if (adminRepo.count() == 0) {
                AdminUser admin = new AdminUser();
                admin.setUsername("admin");
                admin.setPasswordHash(encoder.encode("Admin@123"));
                adminRepo.save(admin);
            }

            if (studentRepo.count() == 0) {
                Student student = new Student();
                student.setStudentCode("STU1001");
                student.setName("Ananya Rao");
                student.setDateOfBirth(LocalDate.of(2002, 5, 14));
                student.setGender(Gender.FEMALE);
                student.setEmail("ananya@example.com");
                student.setMobileNumber("9876543210");
                student.setParentsNames("Ravi Rao and Meena Rao");

                Address address = new Address();
                address.setAddressType(AddressType.PERMANENT);
                address.setLine1("12 MG Road");
                address.setCity("Bengaluru");
                address.setState("Karnataka");
                address.setPostalCode("560068");
                address.setCountry("India");
                student.addAddress(address);
                studentRepo.save(student);
            }

            if (courseRepo.count() == 0) {
                Course java = new Course();
                java.setName("Java Backend Development");
                java.setDescription("Backend development using Java and Spring Boot");
                java.setCourseType("Technical");
                java.setDuration("12 weeks");
                java.setTopics(List.of("Java", "Spring Boot", "REST API", "JPA"));
                courseRepo.save(java);

                Course database = new Course();
                database.setName("Database Fundamentals");
                database.setDescription("Relational database concepts and SQL");
                database.setCourseType("Technical");
                database.setDuration("8 weeks");
                database.setTopics(List.of("SQL", "MySQL", "Database Design"));
                courseRepo.save(database);
            }

            if (enrollmentRepo.count() == 0) {
                Student student = studentRepo.findByStudentCode("STU1001").orElseThrow();
                Course course = courseRepo.findByNameContainingIgnoreCase("Java Backend Development")
                        .stream().findFirst().orElseThrow();
                Enrollment enrollment = new Enrollment();
                enrollment.setStudent(student);
                enrollment.setCourse(course);
                enrollmentRepo.save(enrollment);
            }
        };
    }
}
