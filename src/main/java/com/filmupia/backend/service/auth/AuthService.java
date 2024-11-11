package com.filmupia.backend.service.auth;

import com.filmupia.backend.model.auth.AuthResponse;
import com.filmupia.backend.model.auth.LoginDto;
import com.filmupia.backend.model.auth.RegisterDto;

public interface AuthService {
    AuthResponse register(RegisterDto request);
    AuthResponse login(LoginDto request);
    AuthResponse refreshAccessToken(String refreshToken);
    AuthResponse toggleUserRole(Long userId);
}
