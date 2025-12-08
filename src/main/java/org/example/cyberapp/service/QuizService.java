package org.example.cyberapp.service;

import org.example.cyberapp.model.QuizProgress;
import org.example.cyberapp.repository.QuizProgressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizProgressRepository quizProgressRepository;

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
}
