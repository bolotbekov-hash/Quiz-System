package com.example.quiz.service;

import com.example.quiz.model.Quiz;
import com.example.quiz.repository.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    private Quiz sampleQuiz;

    @BeforeEach
    void setUp() {
        sampleQuiz = Quiz.builder()
                .id(1L)
                .title("Java Basics")
                .description("Test your Java knowledge")
                .category("Programming")
                .build();
    }

    @Test
    void getQuizById_shouldReturnQuiz_whenExists() {
        when(quizRepository.findById(1L)).thenReturn(Optional.of(sampleQuiz));

        Quiz result = quizService.getQuizById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo("Java Basics");
        verify(quizRepository).findById(1L);
    }

    @Test
    void getQuizById_shouldThrowException_whenNotFound() {
        when(quizRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> quizService.getQuizById(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Quiz not found with id: 99");
    }

    @Test
    void getAllQuizzes_shouldReturnFilteredList() {
        when(quizRepository.searchQuizzes("Java", null)).thenReturn(List.of(sampleQuiz));

        List<Quiz> results = quizService.getAllQuizzes("Java", null);

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getCategory()).isEqualTo("Programming");
    }

    @Test
    void createQuiz_shouldSaveAndReturnQuiz() {
        when(quizRepository.save(sampleQuiz)).thenReturn(sampleQuiz);

        Quiz created = quizService.createQuiz(sampleQuiz);

        assertThat(created.getTitle()).isEqualTo("Java Basics");
        verify(quizRepository).save(sampleQuiz);
    }

    @Test
    void deleteQuiz_shouldDelete_whenExists() {
        when(quizRepository.existsById(1L)).thenReturn(true);

        quizService.deleteQuiz(1L);

        verify(quizRepository).deleteById(1L);
    }

    @Test
    void deleteQuiz_shouldThrow_whenNotFound() {
        when(quizRepository.existsById(99L)).thenReturn(false);

        assertThatThrownBy(() -> quizService.deleteQuiz(99L))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Quiz not found with id: 99");

        verify(quizRepository, never()).deleteById(any());
    }
}
