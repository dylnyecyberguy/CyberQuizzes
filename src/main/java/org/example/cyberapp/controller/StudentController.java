package org.example.cyberapp.controller;

import org.example.cyberapp.model.Student;
import org.example.cyberapp.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    // Get all students
    @GetMapping
    public List<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    // Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        try {
            Student student = studentService.getStudentById(id);
            return ResponseEntity.ok(student);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Paginated search
    @GetMapping("/paginated")
    public Page<Student> getStudentsPaginated(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        return studentService.getStudents(search, startDate, endDate, page, size);
    }

    // Create student
    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    // Update student
    @PutMapping("/{id}")
    public Student updateStudentById(@PathVariable Long id, @RequestBody Student student) {
        return studentService.updateStudent(id, student);
    }

    // Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudentById(@PathVariable Long id) {
        studentService.deleteStudentById(id);
        return ResponseEntity.noContent().build();
    }
}
