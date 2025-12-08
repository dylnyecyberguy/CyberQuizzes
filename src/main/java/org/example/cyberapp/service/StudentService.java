package org.example.cyberapp.service;

import org.example.cyberapp.model.Student;
import org.example.cyberapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Create a new student
    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    // Get all students
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    // Get a single student by ID
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    // Update a student
    public Student updateStudent(Long id, Student updatedStudent) {
        Student student = getStudentById(id);
        student.setUsername(updatedStudent.getUsername());
        student.setEmail(updatedStudent.getEmail());
        student.setPassword(updatedStudent.getPassword());
        student.setEnrollmentDate(updatedStudent.getEnrollmentDate());
        return studentRepository.save(student);
    }

    // Delete a student
    public void deleteStudentById(Long id) {
        studentRepository.deleteById(id);
    }

    // Paginated search
    public Page<Student> getStudents(String search, LocalDate startDate, LocalDate endDate, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "username"));
        return studentRepository.findByUsernameContainingIgnoreCaseAndEnrollmentDateBetween(
                search == null ? "" : search,
                startDate == null ? LocalDate.MIN : startDate,
                endDate == null ? LocalDate.MAX : endDate,
                pageable
        );
    }
}
