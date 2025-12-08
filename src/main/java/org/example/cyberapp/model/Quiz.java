package org.example.cyberapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String category;
    private int totalQuestions;

    // Optional: a short description
    private String description;

    // Constructors
    public Quiz() {} // default no-args constructor is required by JPA

    public Quiz(String title, String category, int totalQuestions, String description) {
        this.title = title;
        this.category = category;
        this.totalQuestions = totalQuestions;
        this.description = description;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }
    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
}