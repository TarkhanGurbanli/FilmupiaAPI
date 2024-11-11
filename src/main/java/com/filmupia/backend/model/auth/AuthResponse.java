package com.filmupia.backend.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String statusCode;
    private String statusMessage;
    private String accessToken;
    private String refreshToken;
}
