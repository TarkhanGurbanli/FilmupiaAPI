package com.filmupia.backend.service.auth;

import com.filmupia.backend.model.auth.AuthResponse;
import com.filmupia.backend.model.auth.LoginDto;
import com.filmupia.backend.model.auth.RegisterDto;
import org.springframework.web.multipart.MultipartFile;

public interface AuthService {
    AuthResponse register(RegisterDto request);
    AuthResponse login(LoginDto request);
    AuthResponse refreshAccessToken(String refreshToken);
    AuthResponse toggleUserRole(Long userId);
    String uploadProfileImage(String token, MultipartFile file) throws Exception;
}
