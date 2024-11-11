package com.filmupia.backend.service.auth.impl;

import com.filmupia.backend.constants.Constants;
import com.filmupia.backend.entity.User;
import com.filmupia.backend.entity.enums.Role;
import com.filmupia.backend.exception.FilmupiaApiException;
import com.filmupia.backend.exception.ResourceNotFoundException;
import com.filmupia.backend.model.auth.AuthResponse;
import com.filmupia.backend.model.auth.LoginDto;
import com.filmupia.backend.model.auth.RegisterDto;
import com.filmupia.backend.repository.UserRepository;
import com.filmupia.backend.service.auth.AuthService;
import com.filmupia.backend.service.auth.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;

    @Override
    public AuthResponse register(RegisterDto request) {
        User user = modelMapper.map(request, User.class);
        user.setRole(Role.USER);
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);

        user = userRepository.save(user);

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(
                Constants.STATUS_CREATED, Constants.MESSAGE_REGISTER_SUCCESSFUL, accessToken, refreshToken);
    }

    @Override
    public AuthResponse login(LoginDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new FilmupiaApiException(
                    "Invalid username or password. Please check your credentials and try again.");
        }

        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("Username", "Invalid username or password", request.getUsername()));

        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return new AuthResponse(
                Constants.STATUS_OK,
                Constants.MESSAGE_LOGIN_SUCCESSFUL, accessToken, refreshToken);
    }

    @Override
    public AuthResponse refreshAccessToken(String refreshToken) {
        String username = jwtService.extractUsername(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username not found", username));

        if (jwtService.isRefreshTokenValid(refreshToken, user)) {
            String newAccessToken = jwtService.generateToken(user);
            return new AuthResponse(
                    Constants.STATUS_OK,
                    "New access token generated.",
                    newAccessToken, refreshToken);
        } else {
            throw new FilmupiaApiException("Invalid refresh token.");
        }
    }

    @Override
    public AuthResponse toggleUserRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "ID", userId));

        if (user.getRole() == Role.USER) {
            user.setRole(Role.ADMIN);
        } else if (user.getRole() == Role.ADMIN) {
            user.setRole(Role.USER);
        }

        userRepository.save(user);

        String newAccessToken = jwtService.generateToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);
        return new AuthResponse(
                Constants.STATUS_OK,
                "User role toggled and tokens refreshed.",
                newAccessToken, newRefreshToken);
    }
}