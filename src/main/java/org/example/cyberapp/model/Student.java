package org.example.cyberapp.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;
    private String password;
    private LocalDate enrollmentDate;

    //CONSTRUCTORS

    /**
     * Default constructor (required by JPA)
     */
    public Student() {
    }

    /**
     * Constructor for creating new students (without id)
     */
    public Student(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        // enrollmentDate is intentionally not set here, so it will be null by default.
        // This gives tests full control over its value or allows them to ignore it.
    }

    /**
     * Full constructor for testing purposes
     */
    public Student(Long id, String username, String email, String password, LocalDate enrollmentDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.enrollmentDate = enrollmentDate;
    }

    // ==================== GETTERS ====================

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    // ==================== SETTERS ====================

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }
}