package com.example.quiz.dto;

import lombok.Data;
import java.util.Map;

@Data
public class QuizSubmission {
    private Long quizId;
    private Map<String, String> answers;
}
