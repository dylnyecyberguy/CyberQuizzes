package org.example.cyberapp.controller;

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
        if (studentRepository.findByEmail(student.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }
        studentRepository.save(student);
        return ResponseEntity.ok("Registration successful");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Student loginRequest) {
        Optional<Student> studentOpt = studentRepository.findByEmail(loginRequest.getEmail());
        if (studentOpt.isPresent() && studentOpt.get().getPassword().equals(loginRequest.getPassword())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }
}