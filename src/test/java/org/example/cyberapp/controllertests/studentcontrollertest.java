package org.example.cyberapp.controllertests;

import org.example.cyberapp.controller.StudentController;
import org.example.cyberapp.model.Student;
import org.example.cyberapp.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StudentControllerTest {

    private StudentController studentController;
    private StudentService studentService;

    @BeforeEach
    void setUp() {
        studentService = Mockito.mock(StudentService.class);
        studentController = new StudentController();
        studentController.studentService = studentService; // inject mock
    }

    @Test
    void testGetAllStudents() {
        Student student = new Student();
        student.setFirstName("john");
        student.setLastName("doe");

        when(studentService.getAllStudents()).thenReturn(List.of(student));

        List<Student> result = studentController.getAllStudents();

        assertEquals(1, result.size());
        assertEquals("john", result.get(0).getFirstName());
        assertEquals("doe", result.get(0).getLastName());
        verify(studentService, times(1)).getAllStudents();
    }


    @Test
    void testGetStudentByIdNotFound() {
        // Mock service to throw RuntimeException to simulate not found
        when(studentService.getStudentById(99L)).thenThrow(new RuntimeException("Student not found"));

        ResponseEntity<Student> response = studentController.getStudentById(99L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(studentService, times(1)).getStudentById(99L);
    }

    @Test
    void testGetStudentsPaginated() {
        Student student = new Student();
        student.setFirstName("bob");
        student.setLastName("lee");

        Page<Student> page = new PageImpl<>(List.of(student));
        when(studentService.getStudents("", null, null, 0, 3)).thenReturn(page);

        Page<Student> result = studentController.getStudentsPaginated("", null, null, 0, 3);

        assertEquals(1, result.getContent().size());
        assertEquals("bob", result.getContent().get(0).getFirstName());
        assertEquals("lee", result.getContent().get(0).getLastName());
        verify(studentService, times(1)).getStudents("", null, null, 0, 3);
    }

    @Test
    void testCreateStudent() {
        Student student = new Student();
        student.setFirstName("carol");
        student.setLastName("king");

        when(studentService.createStudent(student)).thenReturn(student);

        Student result = studentController.createStudent(student);

        assertEquals("carol", result.getFirstName());
        assertEquals("king", result.getLastName());
        verify(studentService, times(1)).createStudent(student);
    }

    @Test
    void testUpdateStudentById() {
        Student student = new Student();
        student.setFirstName("dave");
        student.setLastName("brown");

        when(studentService.updateStudent(1L, student)).thenReturn(student);

        Student result = studentController.updateStudentById(1L, student);

        assertEquals("dave", result.getFirstName());
        assertEquals("brown", result.getLastName());
        verify(studentService, times(1)).updateStudent(1L, student);
    }

    @Test
    void testDeleteStudentById() {
        doNothing().when(studentService).deleteStudentById(1L);

        ResponseEntity<Void> response = studentController.deleteStudentById(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(studentService, times(1)).deleteStudentById(1L);
    }
}
