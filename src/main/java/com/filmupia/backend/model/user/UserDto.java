package com.filmupia.backend.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UserDto {
    private String username;
    private String email;
    private Long imageId;
    private String role;
}
