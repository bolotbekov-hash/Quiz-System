package com.example.quiz.service;

import com.example.quiz.dto.QuizSubmission;
import com.example.quiz.model.Question;
import com.example.quiz.model.Quiz;
import com.example.quiz.model.QuizResult;
import com.example.quiz.model.User;
import com.example.quiz.repository.QuizResultRepository;
import com.example.quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class QuizResultService {

    private final QuizResultRepository quizResultRepository;
    private final QuizService quizService;
    private final UserRepository userRepository;

    @Transactional
    public Map<String, Object> submitQuiz(QuizSubmission submission, String username) {
        Quiz quiz = quizService.getQuizById(submission.getQuizId());
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found: " + username));

        int correct = 0;
        int total = quiz.getQuestions().size();
        Map<Long, String> correctAnswersMap = new HashMap<>();

        for (Question q : quiz.getQuestions()) {
            String correctAns = q.getCorrectAnswer();
            correctAnswersMap.put(q.getId(), correctAns);
            String userAnswer = submission.getAnswers().get(q.getId().toString());
            if (userAnswer != null && userAnswer.trim().equalsIgnoreCase(correctAns.trim())) {
                correct++;
            }
        }

        int percentage = total > 0 ? (correct * 100 / total) : 0;

        QuizResult result = QuizResult.builder()
                .user(user)
                .quiz(quiz)
                .score(correct)
                .totalQuestions(total)
                .percentage(percentage)
                .build();
        quizResultRepository.save(result);

        Map<String, Object> response = new HashMap<>();
        response.put("resultId", result.getId());
        response.put("score", correct);
        response.put("total", total);
        response.put("percentage", percentage);
        response.put("correctAnswers", correctAnswersMap);
        return response;
    }

    public List<QuizResult> getMyResults(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return quizResultRepository.findByUser(user);
    }

    public List<QuizResult> getAllResults() {
        return quizResultRepository.findAll();
    }

    public QuizResult getResultById(Long id) {
        return quizResultRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Result not found with id: " + id));
    }

    public void deleteResult(Long id) {
        if (!quizResultRepository.existsById(id)) {
            throw new RuntimeException("Result not found with id: " + id);
        }
        quizResultRepository.deleteById(id);
    }
}
