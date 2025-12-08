package org.example.cyberapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "quiz_progress")
public class QuizProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Link to a student
    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    // Link to an actual quiz
    @ManyToOne
    @JoinColumn(name = "quiz_id", nullable = false)
    private Quiz quiz;

    private int totalQuestions; // Track total questions in the quiz
    private int correctAnswers;  // Track number of correct answers

    private boolean completed;  // New field to track completion status

    public double getProgressPercentage() {
        return totalQuestions == 0 ? 0 : (correctAnswers * 100.0 / totalQuestions);
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public Quiz getQuiz() { return quiz; }
    public void setQuiz(Quiz quiz) { this.quiz = quiz; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public int getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(int correctAnswers) { this.correctAnswers = correctAnswers; }

    // New Getter and Setter for completed status
    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }
}