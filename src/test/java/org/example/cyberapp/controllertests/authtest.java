package org.example.cyberapp.controllertests;

import org.example.cyberapp.controller.AuthController;
import org.example.cyberapp.model.Student;
import org.example.cyberapp.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthControllerTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    //REGISTER TESTS

    @Test
    void testRegister_Success() {
        Student student = new Student();
        student.setEmail("test@example.com");
        student.setPassword("12345");

        when(studentRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.empty());

        ResponseEntity<String> response = authController.register(student);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Registration successful", response.getBody());
        verify(studentRepository, times(1)).save(student);
    }

    @Test
    void testRegister_EmailAlreadyExists() {
        Student student = new Student();
        student.setEmail("test@example.com");

        when(studentRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(student));

        ResponseEntity<String> response = authController.register(student);

        assertEquals(400, response.getStatusCodeValue());
        assertEquals("Email already registered", response.getBody());
        verify(studentRepository, times(0)).save(any());
    }

    //LOGIN TESTS

    @Test
    void testLogin_Success() {
        Student existing = new Student();
        existing.setEmail("test@example.com");
        existing.setPassword("12345");

        Student loginRequest = new Student();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("12345");

        when(studentRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(existing));

        ResponseEntity<String> response = authController.login(loginRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Login successful", response.getBody());
    }

    @Test
    void testLogin_InvalidPassword() {
        Student existing = new Student();
        existing.setEmail("test@example.com");
        existing.setPassword("correct");

        Student loginRequest = new Student();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("wrong");

        when(studentRepository.findByEmail("test@example.com"))
                .thenReturn(Optional.of(existing));

        ResponseEntity<String> response = authController.login(loginRequest);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid email or password", response.getBody());
    }

    @Test
    void testLogin_UserNotFound() {
        Student loginRequest = new Student();
        loginRequest.setEmail("unknown@example.com");
        loginRequest.setPassword("123");

        when(studentRepository.findByEmail("unknown@example.com"))
                .thenReturn(Optional.empty());

        ResponseEntity<String> response = authController.login(loginRequest);

        assertEquals(401, response.getStatusCodeValue());
        assertEquals("Invalid email or password", response.getBody());
    }
}
