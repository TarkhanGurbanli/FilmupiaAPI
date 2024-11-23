package com.filmupia.backend.controller;

import com.filmupia.backend.constants.Constants;
import com.filmupia.backend.model.ResponseModel;
import com.filmupia.backend.model.user.ChangePasswordDto;
import com.filmupia.backend.model.user.ChangeUsernameDto;
import com.filmupia.backend.model.user.UploadImageDto;
import com.filmupia.backend.model.user.UserDto;
import com.filmupia.backend.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User Management")
public class UserController {

    private final UserService userService;

    @PatchMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload a user's profile image",
            description = "Allows a user to upload a new profile image.")
    public ResponseEntity<ResponseModel> uploadImage(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @ModelAttribute UploadImageDto dto
    ) throws IOException {
        userService.uploadImage(token, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ResponseModel(
                        Constants.STATUS_OK,
                        Constants.MESSAGE_CREATED_SUCCESSFULLY
                )
        );
    }

    @PatchMapping("/changePassword")
    @Operation(summary = "Change user password",
            description = "Allows a user to change their password.")
    public ResponseEntity<ResponseModel> changePassword(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @RequestBody ChangePasswordDto request
    ) {
        userService.changePassword(token, request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(
                Constants.STATUS_NO_CONTENT,
                Constants.MESSAGE_UPDATE_SUCCESSFUL
        ));
    }

    @PatchMapping("/changeUsername")
    @Operation(summary = "Change user username",
            description = "Allows a user to change their username.")
    public ResponseEntity<ResponseModel> changeUsername(
            @Parameter(description = "Authorization token") @RequestHeader("Authorization") String token,
            @RequestBody ChangeUsernameDto request
    ) {
        userService.changeUsername(token, request);
        return ResponseEntity.status(HttpStatus.OK).body(new ResponseModel(
                Constants.STATUS_NO_CONTENT,
                Constants.MESSAGE_UPDATE_SUCCESSFUL
        ));
    }

    @GetMapping("/admin")
    @Operation(summary = "Get all users",
            description = "Retrieves a list of all users in the system for admin.")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
