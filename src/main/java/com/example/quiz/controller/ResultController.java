package com.example.quiz.controller;

import com.example.quiz.model.QuizResult;
import com.example.quiz.service.QuizResultService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/results")
@RequiredArgsConstructor
@Tag(name = "Results", description = "Quiz results management")
@SecurityRequirement(name = "bearerAuth")
public class ResultController {

    private final QuizResultService quizResultService;

    @GetMapping("/my")
    @Operation(summary = "Get current user's quiz results")
    public ResponseEntity<List<QuizResult>> getMyResults(Authentication authentication) {
        return ResponseEntity.ok(quizResultService.getMyResults(authentication.getName()));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(summary = "Get all results (MANAGER, ADMIN only)")
    public ResponseEntity<List<QuizResult>> getAllResults() {
        return ResponseEntity.ok(quizResultService.getAllResults());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN')")
    @Operation(summary = "Get a result by ID (MANAGER, ADMIN only)")
    public ResponseEntity<QuizResult> getResultById(@PathVariable Long id) {
        return ResponseEntity.ok(quizResultService.getResultById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a result (ADMIN only)")
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        quizResultService.deleteResult(id);
        return ResponseEntity.noContent().build();
    }
}
