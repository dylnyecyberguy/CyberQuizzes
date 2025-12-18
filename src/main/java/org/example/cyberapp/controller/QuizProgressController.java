package org.example.cyberapp.controller;

import org.example.cyberapp.model.QuizProgress;
import org.example.cyberapp.model.Student;
import org.example.cyberapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/quiz-progress")
public class QuizProgressController {

    @Autowired
    private QuizService quizService;

    // Get all quiz progress for a student
    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<QuizProgress>> getProgressByStudent(@PathVariable Long studentId) {
        List<QuizProgress> progressList = quizService.getProgressByStudent(studentId);
        return ResponseEntity.ok(progressList);
    }

    // Get quiz progress for a specific student and quiz
    @GetMapping("/student/{studentId}/quiz/{quizId}")
    public ResponseEntity<QuizProgress> getProgressByStudentAndQuiz(
            @PathVariable Long studentId,
            @PathVariable Long quizId
    ) {
        QuizProgress progress = quizService.getProgressByStudentAndQuiz(studentId, quizId);
        if (progress == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(progress);
    }

    // Create or update quiz progress
    @PostMapping
    public ResponseEntity<QuizProgress> createOrUpdateProgress(@RequestBody QuizProgress quizProgress) {
        QuizProgress saved = quizService.saveProgress(quizProgress);
        return ResponseEntity.ok(saved);
    }

    @PostMapping("/complete")
    public ResponseEntity<QuizProgress> completeQuiz(@RequestBody QuizProgress progress) {
        QuizProgress saved = quizService.submitQuiz(
                progress.getStudent().getId(),
                progress.getQuiz().getId(),
                progress.getCorrectAnswers()
        );
        return ResponseEntity.ok(saved);
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        quizService.deleteProgress(id);
        return ResponseEntity.noContent().build();
    }
}
