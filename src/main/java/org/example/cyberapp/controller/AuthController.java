package org.example.cyberapp.controller;

import jakarta.servlet.http.HttpSession;
import org.example.cyberapp.model.Student;
import org.example.cyberapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private StudentRepository studentRepository;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Student student) {
        Optional<Student> existing = studentRepository.findByEmail(student.getEmail());
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        studentRepository.save(student);
        return ResponseEntity.ok("Registration successful");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Student loginRequest, HttpSession session) {
        Optional<Student> studentOpt = studentRepository.findByEmail(loginRequest.getEmail());
        if (studentOpt.isPresent() && studentOpt.get().getPassword().equals(loginRequest.getPassword())) {

            Student student = studentOpt.get();

            // Store user info in session
            session.setAttribute("studentId", student.getId());
            session.setAttribute("username", student.getUsername());
            session.setAttribute("email", student.getEmail());

            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }

    @GetMapping("/me")
    public ResponseEntity<?> getCurrentUser(HttpSession session) {
        Long studentId = (Long) session.getAttribute("studentId");
        if (studentId == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }

        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            return ResponseEntity.status(401).body("User not found");
        }

        return ResponseEntity.ok(studentOpt.get());
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logout successful");
    }
}