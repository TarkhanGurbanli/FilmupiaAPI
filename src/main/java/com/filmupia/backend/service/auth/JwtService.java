package com.filmupia.backend.service.auth;

import com.filmupia.backend.entity.User;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;
import java.util.function.Function;

public interface JwtService {
    String extractUsername(String token);
    <T> T extractClaim(String token, Function<Claims, T> resolver);
    List<String> getRolesFromToken(String token);
    boolean isValid(String token, UserDetails user);
    String generateToken(User user);
    String generateRefreshToken(User user);
    boolean isRefreshTokenValid(String refreshToken, User user);
}
