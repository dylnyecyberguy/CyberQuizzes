package org.example.cyberapp.controller;

import org.example.cyberapp.model.Student;
import org.example.cyberapp.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void register_EmailAlreadyExists_ReturnsBadRequest() {
        // Arrange
        Student existingStudent = new Student();
        existingStudent.setEmail("test@example.com");

        Student newStudent = new Student();
        newStudent.setEmail("test@example.com");

        when(studentRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(existingStudent));

        // Act
        ResponseEntity<String> response = authController.register(newStudent);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email already registered", response.getBody());
    }

    @Test
    public void register_NewEmail_ReturnsSuccess() {
        // Arrange
        Student newStudent = new Student();
        newStudent.setEmail("new@example.com");

        when(studentRepository.findByEmail("new@example.com"))
                .thenReturn(Optional.empty());

        // Act
        ResponseEntity<String> response = authController.register(newStudent);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Registration successful", response.getBody());
    }
}