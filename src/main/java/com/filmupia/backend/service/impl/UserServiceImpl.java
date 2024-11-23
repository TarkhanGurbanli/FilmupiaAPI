package com.filmupia.backend.service.impl;

import com.filmupia.backend.entity.Image;
import com.filmupia.backend.entity.User;
import com.filmupia.backend.entity.enums.ImageType;
import com.filmupia.backend.exception.FilmupiaApiException;
import com.filmupia.backend.exception.ResourceNotFoundException;
import com.filmupia.backend.model.auth.JwtModel;
import com.filmupia.backend.model.user.ChangePasswordDto;
import com.filmupia.backend.model.user.ChangeUsernameDto;
import com.filmupia.backend.model.user.UploadImageDto;
import com.filmupia.backend.model.user.UserDto;
import com.filmupia.backend.repository.UserRepository;
import com.filmupia.backend.service.UserService;
import com.filmupia.backend.service.auth.JwtService;
import com.filmupia.backend.service.auth.utils.JwtUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final ImageServiceImpl imageService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final JwtService jwtService;

    @Override
    public void changePassword(String token, ChangePasswordDto request) {
        try {
            JwtModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId));

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUsername(),
                            request.getOldPassword()
                    )
            );

            if (request.getNewPassword() != null && !request.getNewPassword().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(request.getNewPassword());
                user.setPassword(hashedPassword);
            }

            userRepository.save(user);

            jwtService.generateToken(user);
            jwtService.generateRefreshToken(user);

        } catch (Exception e) {
            throw new FilmupiaApiException("Error : " + e.getMessage());
        }
    }

    @Override
    public void changeUsername(String token, ChangeUsernameDto request) {
        try {
            JwtModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId));

            if (request.getNewUsername() != null && !request.getNewUsername().isEmpty()) {
                user.setUsername(request.getNewUsername());
            } else {
                throw new FilmupiaApiException("New username cannot be empty");
            }

            userRepository.save(user);

            jwtService.generateToken(user);
            jwtService.generateRefreshToken(user);

        } catch (Exception e) {
            throw new FilmupiaApiException("Error changing username: " + e.getMessage());
        }
    }

    @Override
    public void uploadImage(String token, UploadImageDto dto) throws IOException {
        try {
            JwtModel jwtModel = jwtUtil.decodeToken(token);
            Long userId = jwtModel.getUserId();

            User user = userRepository.findById(userId).orElseThrow(
                    () -> new ResourceNotFoundException("User", "ID", userId));

            if (dto.getImage() != null && !dto.getImage().isEmpty()) {
                File tempFile = File.createTempFile("user-", dto.getImage().getOriginalFilename());
                dto.getImage().transferTo(tempFile);

                Image profileImage = imageService.uploadImageToDrive(tempFile, ImageType.USER);

                user.setProfileImage(profileImage);

                tempFile.delete();
            }

            userRepository.save(user);
        } catch (Exception e) {
            throw new FilmupiaApiException("Error getting profile image: " + e.getMessage());
        }
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());    }
}
