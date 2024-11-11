package com.filmupia.backend.model.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class JwtModel {
    private List<String> roles;
    private Long userId;
}
