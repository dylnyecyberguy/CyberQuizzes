package org.example.cyberapp.service;

import org.example.cyberapp.model.Quiz;
import org.example.cyberapp.model.QuizProgress;
import org.example.cyberapp.model.Student;
import org.example.cyberapp.repository.QuizProgressRepository;
import org.example.cyberapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizProgressRepository quizProgressRepository;

    @Autowired
    private QuizRepository quizRepository;

    // Get all progress for a student
    public List<QuizProgress> getProgressByStudent(Long studentId) {
        return quizProgressRepository.findByStudent_Id(studentId);
    }

    // Get progress for a specific student and quiz
    public QuizProgress getProgressByStudentAndQuiz(Long studentId, Long quizId) {
        return quizProgressRepository.findByStudent_IdAndQuiz_Id(studentId, quizId);
    }

    // Save or update progress
    public QuizProgress saveProgress(QuizProgress progress) {
        return quizProgressRepository.save(progress);
    }

    // Delete progress
    public void deleteProgress(Long id) {
        quizProgressRepository.deleteById(id);
    }

    // Load quiz by ID
    public Quiz getQuizById(Long quizId) {
        return quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + quizId));
    }

    // Submit a quiz for a student
    public QuizProgress submitQuiz(Long studentId, Long quizId, int correctAnswers) {
        QuizProgress progress = new QuizProgress();

        // Link student and quiz
        progress.setStudent(new Student());
        progress.getStudent().setId(studentId);

        progress.setQuiz(new Quiz());
        progress.getQuiz().setId(quizId);

        // Track results
        progress.setTotalQuestions(getQuizById(quizId).getTotalQuestions());
        progress.setCorrectAnswers(correctAnswers);
        progress.setCompleted(true);

        return quizProgressRepository.save(progress);
    }
}
