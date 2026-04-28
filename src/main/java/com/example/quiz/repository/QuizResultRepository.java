package com.example.quiz.repository;

import com.example.quiz.model.QuizResult;
import com.example.quiz.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {
    List<QuizResult> findByUser(User user);
    List<QuizResult> findByQuizId(Long quizId);
}
