package com.filmupia.backend.service;

import com.filmupia.backend.model.user.ChangePasswordDto;
import com.filmupia.backend.model.user.ChangeUsernameDto;
import com.filmupia.backend.model.user.UploadImageDto;
import com.filmupia.backend.model.user.UserDto;

import java.io.IOException;
import java.util.List;

public interface UserService {
    void changePassword(String token, ChangePasswordDto request);
    void changeUsername(String token, ChangeUsernameDto request);
    void uploadImage(String token, UploadImageDto dto) throws IOException;
    List<UserDto> getAllUsers();}
