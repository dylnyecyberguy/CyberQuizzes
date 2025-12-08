package org.example.cyberapp.repository;

import org.example.cyberapp.model.QuizProgress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface QuizProgressRepository extends JpaRepository<QuizProgress, Long> {

    List<QuizProgress> findByStudent_Id(Long studentId);

    QuizProgress findByStudent_IdAndQuiz_Id(Long studentId, Long quizId);
}