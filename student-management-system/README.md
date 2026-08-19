# Student Management System

A Spring Boot REST API for managing student admissions, addresses, courses and enrollments.

## What is included

- Spring Boot REST API
- Entity / Repository / Service / Controller layers
- JPA with Hibernate
- H2 file database for zero-setup local execution
- Spring Security with JWT authentication
- Admin login
- Student verification using `studentCode` + `dateOfBirth`
- Student admission with one-to-many addresses
- Course management
- Student-course enrollment management
- Search students by name
- Search students assigned to a course
- Student profile update
- Student course search and leave-course operation
- Swagger/OpenAPI
- Unit test for the service layer
- Postman collection

## Requirements

- JDK 17+
- Maven 3.9+
- Git
- Postman (optional, for API testing)

Check your installation:

```bash
java -version
mvn -version
git --version
```

## Run locally

From the project directory:

```bash
mvn clean test
mvn spring-boot:run
```

The API starts at:

`http://localhost:8080`

Swagger UI:

`http://localhost:8080/swagger-ui.html`

H2 console:

`http://localhost:8080/h2-console`

H2 JDBC URL:

`jdbc:h2:file:./data/studentdb`

Username:

`sa`

Password:

leave blank.

## Seeded test users

Admin:

- username: `admin`
- password: `Admin@123`

Student:

- studentCode: `STU1001`
- dateOfBirth: `2002-05-14`

These values are only for local demonstration. Change the seed data and JWT secret before using this outside a local assignment.

## API flow

### 1. Admin login

`POST /api/auth/admin/login`

```json
{
  "username": "admin",
  "password": "Admin@123"
}
```

Copy the returned JWT.

### 2. Student verification

`POST /api/auth/student/verify`

```json
{
  "studentCode": "STU1001",
  "dateOfBirth": "2002-05-14"
}
```

Copy the returned JWT.

### 3. Use the token

For protected endpoints add:

```text
Authorization: Bearer <token>
```

Admin endpoints require an ADMIN token.

Student endpoints require a STUDENT token.

## Main endpoints

### Authentication

- `POST /api/auth/admin/login`
- `POST /api/auth/student/verify`

### Admin

- `POST /api/admin/students`
- `POST /api/admin/courses`
- `POST /api/admin/students/{studentId}/courses/{courseId}`
- `GET /api/admin/students?name=Ananya`
- `GET /api/admin/courses/{courseId}/students`
- `GET /api/admin/courses?q=Java`
- `GET /api/admin/courses?topic=Spring`

### Student

- `GET /api/student/profile`
- `PUT /api/student/profile`
- `GET /api/student/courses`
- `GET /api/student/courses?topic=Java`
- `DELETE /api/student/courses/{courseId}`

## Database relationships

- Student -> Address: one-to-many
- Student -> Course: many-to-many represented by the `Enrollment` entity
- Course -> Topics: element collection

Using an explicit `Enrollment` entity instead of a direct `@ManyToMany` makes the relationship easier to extend later with fields such as enrollment date or status.

## Example student admission

```json
{
  "name": "Rahul Kumar",
  "dateOfBirth": "2001-08-20",
  "gender": "MALE",
  "studentCode": "STU2001",
  "addresses": [
    {
      "addressType": "PERMANENT",
      "line1": "10 Main Road",
      "line2": "Near Park",
      "city": "Bengaluru",
      "state": "Karnataka",
      "postalCode": "560038",
      "country": "India"
    },
    {
      "addressType": "CURRENT",
      "line1": "45 Residency Road",
      "line2": null,
      "city": "Bengaluru",
      "state": "Karnataka",
      "postalCode": "560025",
      "country": "India"
    }
  ]
}
```

## Example course

```json
{
  "name": "Spring Boot Microservices",
  "description": "Building REST services with Spring Boot",
  "courseType": "Technical",
  "duration": "10 weeks",
  "topics": [
    "Spring Boot",
    "Microservices",
    "REST API"
  ]
}
```

## Suggested implementation order

1. Run the application and verify the seeded admin/student.
2. Test admin login.
3. Create a course.
4. Create a student with multiple addresses.
5. Assign the course to the student.
6. Search the student by name.
7. Search students by course.
8. Verify the student.
9. Read and update the student profile.
10. Search assigned courses by topic.
11. Leave a course.
12. Run `mvn clean test`.

## Notes about originality

This repository is intended as a reference implementation. Before submitting an employment assignment, read every class, make design decisions you can explain, rename/sample data as appropriate, and test the application yourself. Do not claim work you cannot explain in an interview.
