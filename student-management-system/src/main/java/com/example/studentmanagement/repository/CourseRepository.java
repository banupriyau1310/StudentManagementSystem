package com.example.studentmanagement.repository;

import com.example.studentmanagement.entity.Course;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByNameIgnoreCase(String name);
    List<Course> findByNameContainingIgnoreCase(String name);

    @Query("select distinct c from Course c join c.topics t where lower(t) like lower(concat('%', :topic, '%'))")
    List<Course> findByTopic(@Param("topic") String topic);
}
