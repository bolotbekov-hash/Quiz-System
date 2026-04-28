package com.example.quiz.service;

import com.example.quiz.dto.AuthRequest;
import com.example.quiz.dto.AuthResponse;
import com.example.quiz.dto.RegisterRequest;
import com.example.quiz.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

//    public String register(RegisterRequest request) {
//        userService.registerUser(request.getUsername(), request.getPassword());
//        return "Registration successful";
//    }

    public AuthResponse register(RegisterRequest request) {
        userService.registerUser(request.getUsername(), request.getPassword());
        // Сразу логинем и возвращаем токен
        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponse(token);
    }

    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );
        String token = jwtUtil.generateToken(request.getUsername());
        return new AuthResponse(token);
    }
}
