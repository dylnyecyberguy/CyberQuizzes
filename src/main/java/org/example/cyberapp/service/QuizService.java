package org.example.cyberapp.service;

import org.example.cyberapp.model.Quiz;
import org.example.cyberapp.model.QuizProgress;
import org.example.cyberapp.model.Student;
import org.example.cyberapp.repository.QuizProgressRepository;
import org.example.cyberapp.repository.QuizRepository;
import org.example.cyberapp.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizProgressRepository quizProgressRepository;

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private StudentRepository studentRepository;

    public List<QuizProgress> getProgressByStudent(Long studentId) {
        return quizProgressRepository.findByStudent_Id(studentId);
    }

    public QuizProgress getProgressByStudentAndQuiz(Long studentId, Long quizId) {
        return quizProgressRepository.findByStudent_IdAndQuiz_Id(studentId, quizId);
    }

    public QuizProgress saveProgress(QuizProgress progress) {
        return quizProgressRepository.save(progress);
    }

    public void deleteProgress(Long id) {
        quizProgressRepository.deleteById(id);
    }

    public Quiz getQuizById(Long quizId) {
        return quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found: " + quizId));
    }

    public QuizProgress submitQuiz(Long studentId, Long quizId, int correctAnswers) {

        // Load student and quiz
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found: " + studentId));

        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new RuntimeException("Quiz not found: " + quizId));

        // Load existing progress or create new
        QuizProgress progress = quizProgressRepository.findByStudent_IdAndQuiz_Id(studentId, quizId);

        if (progress == null) {
            progress = new QuizProgress();
            progress.setStudent(student);
            progress.setQuiz(quiz);
            progress.setTotalQuestions(quiz.getTotalQuestions());
        }

        progress.setCorrectAnswers(correctAnswers);
        progress.setCompleted(true);

        return quizProgressRepository.save(progress);
    }}