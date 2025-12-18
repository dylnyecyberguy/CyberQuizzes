package org.example.cyberapp.controller;

import org.example.cyberapp.model.Student;
import org.example.cyberapp.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private Student testStudent;

    @BeforeEach
    public void setUp() {
        testStudent = new Student();
        testStudent.setUsername("testuser");
        testStudent.setEmail("test@example.com");
        testStudent.setPassword("password123");
    }

    // GET ALL STUDENTS

    @Test
    public void getAllStudents_ReturnsListOfStudents() {
        // Arrange
        Student student1 = new Student();
        student1.setUsername("user1");

        Student student2 = new Student();
        student2.setUsername("user2");

        List<Student> students = Arrays.asList(student1, student2);
        when(studentService.getAllStudents()).thenReturn(students);

        // Act
        List<Student> result = studentController.getAllStudents();

        // Assert
        assertEquals(2, result.size());
        verify(studentService, times(1)).getAllStudents();
    }

    //GET STUDENT BY ID

    @Test
    public void getStudentById_Found_ReturnsStudent() {
        // Arrange
        when(studentService.getStudentById(1L)).thenReturn(testStudent);

        // Act
        ResponseEntity<Student> response = studentController.getStudentById(1L);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testStudent, response.getBody());
    }

    @Test
    public void getStudentById_NotFound_Returns404() {
        // Arrange
        when(studentService.getStudentById(99L))
                .thenThrow(new RuntimeException("Student not found"));

        // Act
        ResponseEntity<Student> response = studentController.getStudentById(99L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    //  CREATE STUDENT

    @Test
    public void createStudent_ReturnsCreatedStudent() {
        // Arrange
        when(studentService.createStudent(testStudent)).thenReturn(testStudent);

        // Act
        Student result = studentController.createStudent(testStudent);

        // Assert
        assertEquals("testuser", result.getUsername());
        assertEquals("test@example.com", result.getEmail());
        verify(studentService, times(1)).createStudent(testStudent);
    }

    // DELETE STUDENT

    @Test
    public void deleteStudentById_ReturnsNoContent() {
        // Arrange
        doNothing().when(studentService).deleteStudentById(1L);

        // Act
        ResponseEntity<Void> response = studentController.deleteStudentById(1L);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(studentService, times(1)).deleteStudentById(1L);
    }
}