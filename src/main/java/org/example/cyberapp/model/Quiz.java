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


    private String description;

    //  stores the correct answer indices
    @Column(length = 1024)
    private String correctAnswerIndices;

    // Constructors
    public Quiz() {} // default no-args constructor

    public Quiz(String title, String category, int totalQuestions, String description, String correctAnswerIndices) {
        this.title = title;
        this.category = category;
        this.totalQuestions = totalQuestions;
        this.description = description;
        this.correctAnswerIndices = correctAnswerIndices;
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

    public String getCorrectAnswerIndices() {
        return correctAnswerIndices;
    }
    public void setCorrectAnswerIndices(String correctAnswerIndices) {
        this.correctAnswerIndices = correctAnswerIndices;
    }
}
