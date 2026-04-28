package com.example.quiz.service;

import com.example.quiz.model.Quiz;
import com.example.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    public List<Quiz> getAllQuizzes(String search, String category) {
        // Если фильтры не переданы — возвращаем все квизы через findAll()
        // (JPQL ':param IS NULL' не работает надёжно с PostgreSQL через Hibernate)
        if ((search == null || search.isBlank()) && (category == null || category.isBlank())) {
            return quizRepository.findAll();
        }
        return quizRepository.searchQuizzes(search, category);
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Quiz not found with id: " + id));
    }

    @Transactional
    public Quiz createQuiz(Quiz quiz) {
        if (quiz.getQuestions() != null) {
            quiz.getQuestions().forEach(q -> q.setQuiz(quiz));
        }
        return quizRepository.save(quiz);
    }

    @Transactional
    public Quiz updateQuiz(Long id, Quiz updated) {
        Quiz existing = getQuizById(id);
        existing.setTitle(updated.getTitle());
        existing.setDescription(updated.getDescription());
        existing.setCategory(updated.getCategory());
        if (updated.getQuestions() != null) {
            existing.getQuestions().clear();
            updated.getQuestions().forEach(q -> {
                q.setQuiz(existing);
                existing.getQuestions().add(q);
            });
        }
        return quizRepository.save(existing);
    }

    public void deleteQuiz(Long id) {
        if (!quizRepository.existsById(id)) {
            throw new RuntimeException("Quiz not found with id: " + id);
        }
        quizRepository.deleteById(id);
    }
}
