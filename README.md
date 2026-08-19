StudentManagementSystem
A Spring Boot Rest API for managing the students, courses and authentication.

Features 
- Student CRUD operations (add, update, search by name/course)
- Course management (add courses, assign to students)
- H2 in-memory database with auto table generation
   
Tech Stack
Java 17+, Maven 3.9+, Spring Data JPA, H2 Database (default), Postman (used for API testing)

Requirements
- JDK 17+
- Maven 3.9+
- Postman (optional, for testing)

Run locally
From the project directory: mvn clean, mvn spring-boot:run

API - http://localhost:8080 H2 Console - http://localhost:8080/h2-console, username: sa, password: 

API Endpoints
Authentication
POST /api/auth/admin/login - Admin login
POST /api/auth/student/verify - Student validation

Admin
POST /api/admin/createStudent - Create Student
POST /api/admin/createCourse - Create Course
POST /api/admin/students/{{studentId}}/courses/{{courseId}} - Assign course to student
GET /api/admin/students?name={{studentName}} - Get Student Details
GET /api/admin/courses/{{courseId}}/students - Get Students by Course

Students
GET /api/student/profile - Get Profile
PUT /api/student/profile - Update Profile
GET /api/student/courses - Get Courses
DEL /api/student/courses/{{courseId}} - Leave course

Author
Banu Priya U
GitHub: banupriyau1310 (https://github.com/banupriyau1310)

