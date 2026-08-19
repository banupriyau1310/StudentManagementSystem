package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByStudentCode(String studentCode);
    Optional<Student> findByStudentCode(String studentCode);
    List<Student> findByNameContainingIgnoreCase(String name);

    @Query("select distinct s from Student s join s.enrollments e where e.course.id = :courseId")
    List<Student> findByCourseId(@Param("courseId") Long courseId);

    Optional<Student> findByStudentCodeAndDateOfBirth(String studentCode, LocalDate dateOfBirth);
}
