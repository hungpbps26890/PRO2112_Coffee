package com.poly.coffee.demo;

import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @PostMapping
    public ApiResponse<Student> createStudent(@RequestBody Student request) {

        Student student = studentRepository.save(request);

        Student newStudent = studentRepository.findById(student.getId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        newStudent.setName(request.getName());

        newStudent.getStudentCourses().addAll(
                request.getStudentCourses().stream()
                        .map(studentCourse -> {
                            Course course = courseRepository.findById(studentCourse.getCourse().getId())
                                    .orElseThrow(() -> new RuntimeException("Course not found"));

                            StudentCourse newStudentCourse = new StudentCourse();

                            newStudentCourse.setId(new StudentCourseKey(newStudent.getId(), course.getId()));

                            newStudentCourse.setStudent(newStudent);
                            newStudentCourse.setCourse(course);
                            newStudentCourse.setRating(studentCourse.getRating());

                            return studentCourseRepository.save(newStudentCourse);
                        }).toList());

        return ApiResponse.<Student>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Create new student successfully")
                .result(studentRepository.save(newStudent)).build();
    }

    @GetMapping
    public ApiResponse<List<Student>> getAllStudents() {
        return ApiResponse.<List<Student>>builder()
                .result(studentRepository.findAll()).build();
    }

    @GetMapping("/{id}")
    public ApiResponse<Student> getStudentById(@PathVariable Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return ApiResponse.<Student>builder()
                .result(student)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<Student> updateStudent(
            @PathVariable Long id,
            @RequestBody Student request
    ) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setName(request.getName());

        Student updatedStudent = studentRepository.save(student);

        Set<StudentCourse> updatedStudentCourses = request.getStudentCourses().stream()
                .map(studentCourse -> {
                    Course course = courseRepository.findById(studentCourse.getCourse().getId())
                            .orElseThrow(() -> new RuntimeException("Course not found"));

                    StudentCourse newStudentCourse = new StudentCourse();

                    newStudentCourse.setId(new StudentCourseKey(updatedStudent.getId(), course.getId()));

                    newStudentCourse.setStudent(updatedStudent);
                    newStudentCourse.setCourse(course);
                    newStudentCourse.setRating(studentCourse.getRating());

                    return newStudentCourse;
                }).collect(Collectors.toSet());

        updatedStudent.getStudentCourses().addAll(updatedStudentCourses);

        return ApiResponse.<Student>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Update student successfully")
                .result(studentRepository.save(updatedStudent))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteStudent(@PathVariable long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        studentRepository.delete(student);

        return ApiResponse.<String>builder().message("Delete student successfully").build();
    }
}
