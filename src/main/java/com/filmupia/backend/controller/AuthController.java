package com.filmupia.backend.controller;

import com.filmupia.backend.model.auth.AuthResponse;
import com.filmupia.backend.model.auth.LoginDto;
import com.filmupia.backend.model.auth.RegisterDto;
import com.filmupia.backend.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auths")
@Tag(name = "Authentication", description = "Endpoints for user authentication and authorization")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "Register a new user",
            description = "Allows a new user to register by providing necessary information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterDto request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "User login", description = "Logs in a user by validating credentials.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginDto request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Refresh access token",
            description = "Generates a new access token using a valid refresh token.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Access token refreshed successfully"),
            @ApiResponse(responseCode = "403", description = "Invalid or expired refresh token")
    })
    @PostMapping("/admin/refresh-token")
    public ResponseEntity<AuthResponse> refreshAccessToken(@RequestBody String refreshToken) {
        AuthResponse response = authService.refreshAccessToken(refreshToken);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Toggle user role",
            description = "Toggles the role of a user by user ID, promoting or demoting their access level.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User role toggled successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @PostMapping("/admin/toggle-role/{userId}")
    public ResponseEntity<AuthResponse> toggleUserRole(@PathVariable Long userId) {
        AuthResponse response = authService.toggleUserRole(userId);
        return ResponseEntity.ok(response);
    }
}
