package com.filmupia.backend.service.auth.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.filmupia.backend.exception.FilmupiaApiException;
import com.filmupia.backend.model.auth.JwtModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final ObjectMapper objectMapper;

    public JwtModel decodeToken(String authHeader) throws Exception {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new FilmupiaApiException("Invalid Authorization header.");
        }

        String token = authHeader.substring(7);
        String[] chunks = token.split("\\.");
        if (chunks.length != 3) {
            throw new FilmupiaApiException("Invalid JWT token.");
        }

        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        return objectMapper.readValue(payload, JwtModel.class);
    }
}