package com.example.studentmanagement.service;

import com.example.studentmanagement.dto.*;
import com.example.studentmanagement.entity.*;
import com.example.studentmanagement.exception.BadRequestException;
import com.example.studentmanagement.exception.ResourceNotFoundException;
import com.example.studentmanagement.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    public StudentService(StudentRepository studentRepository, CourseRepository courseRepository,
                          EnrollmentRepository enrollmentRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Transactional
    public StudentResponse create(CreateStudentRequest request) {
        if (studentRepository.existsByStudentCode(request.studentCode())) {
            throw new BadRequestException("Student code already exists");
        }
        Student student = new Student();
        student.setStudentCode(request.studentCode().trim());
        student.setName(request.name().trim());
        student.setDateOfBirth(request.dateOfBirth());
        student.setGender(request.gender());

        if (request.addresses() != null) {
            request.addresses().forEach(a -> student.addAddress(toAddress(a)));
        }
        return toResponse(studentRepository.save(student));
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> searchByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public List<StudentResponse> findByCourse(Long courseId) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));
        return studentRepository.findByCourseId(courseId)
                .stream().map(this::toResponse).toList();
    }

    @Transactional
    public StudentResponse updateProfile(Long studentId, UpdateStudentProfileRequest request) {
        Student student = getStudent(studentId);
        if (request.email() != null) student.setEmail(request.email());
        if (request.mobileNumber() != null) student.setMobileNumber(request.mobileNumber());
        if (request.parentsNames() != null) student.setParentsNames(request.parentsNames());

        if (request.addresses() != null) {
            student.getAddresses().clear();
            request.addresses().forEach(a -> student.addAddress(toAddress(a)));
        }
        return toResponse(student);
    }

    @Transactional(readOnly = true)
    public StudentResponse getProfile(Long studentId) {
        return toResponse(getStudent(studentId));
    }

    @Transactional
    public MessageResponse assignCourse(Long studentId, Long courseId) {
        Student student = getStudent(studentId);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + courseId));

        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new BadRequestException("Student is already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollmentRepository.save(enrollment);
        return new MessageResponse("Course assigned successfully");
    }

    @Transactional
    public MessageResponse leaveCourse(Long studentId, Long courseId) {
        Enrollment enrollment = enrollmentRepository.findByStudentIdAndCourseId(studentId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Enrollment not found"));
        enrollmentRepository.delete(enrollment);
        return new MessageResponse("Course left successfully");
    }

    @Transactional(readOnly = true)
    public List<CourseResponse> getAssignedCourses(Long studentId, String topic) {
        Student student = getStudent(studentId);
        return student.getEnrollments().stream()
                .map(Enrollment::getCourse)
                .filter(course -> topic == null || course.getTopics().stream()
                        .anyMatch(t -> t.equalsIgnoreCase(topic) || t.toLowerCase().contains(topic.toLowerCase())))
                .map(this::toCourseResponse)
                .toList();
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
    }

    private Address toAddress(AddressRequest request) {
        Address address = new Address();
        address.setAddressType(request.addressType());
        address.setLine1(request.line1());
        address.setLine2(request.line2());
        address.setCity(request.city());
        address.setState(request.state());
        address.setPostalCode(request.postalCode());
        address.setCountry(request.country() == null ? "India" : request.country());
        return address;
    }

    public StudentResponse toResponse(Student student) {
        List<AddressResponse> addresses = student.getAddresses().stream()
                .map(a -> new AddressResponse(a.getId(), a.getAddressType(), a.getLine1(), a.getLine2(),
                        a.getCity(), a.getState(), a.getPostalCode(), a.getCountry()))
                .toList();

        List<CourseResponse> courses = student.getEnrollments().stream()
                .map(Enrollment::getCourse)
                .map(this::toCourseResponse)
                .toList();

        return new StudentResponse(student.getId(), student.getStudentCode(), student.getName(),
                student.getDateOfBirth(), student.getGender(), student.getEmail(),
                student.getMobileNumber(), student.getParentsNames(), addresses, courses);
    }

    private CourseResponse toCourseResponse(Course c) {
        return new CourseResponse(c.getId(), c.getName(), c.getDescription(),
                c.getCourseType(), c.getDuration(), c.getTopics());
    }
}
