package com.poly.coffee.controller;

import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.request.ChangePasswordRequest;
import com.poly.coffee.dto.request.UserCreationRequest;
import com.poly.coffee.dto.request.UserUpdateMyInfoRequest;
import com.poly.coffee.dto.request.UserUpdateRequest;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.dto.response.UserResponse;
import com.poly.coffee.entity.User;
import com.poly.coffee.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RestController
@RequestMapping("/api/users")
@Tag(name = "User Controller")
public class UserController {

    UserService userService;

    @Operation(summary = "Create a new user")
    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createRequest(request));
        apiResponse.setCode(StatusCode.SUCCESS_CODE);
        apiResponse.setMessage("Create new user successfully!");

        return apiResponse;
    }

    @Operation(summary = "Get All Users")
    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Username: " + authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .result(userService.getAllUsers())
                .build();
    }

    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.getUserById(id))
                .build();
    }

    @Operation(summary = "Get user info")
    @GetMapping("/my-info")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.getMyInfo())
                .build();
    }

    @Operation(summary = "Update user")
    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request
    ) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.updateUser(id, request));
        apiResponse.setMessage("Update user successfully!");
        apiResponse.setCode(StatusCode.SUCCESS_CODE);

        return apiResponse;
    }

    @Operation(summary = "Update user info")
    @PutMapping("/my-info")
    public ApiResponse<UserResponse> updateMyInfo(
            @RequestBody @Valid UserUpdateMyInfoRequest request
    ) {

        return ApiResponse.<UserResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Update user info successfully")
                .result(userService.updateMyInfo(request))
                .build();
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("User was deleted successfully")
                .build();
    }

    @Operation(summary = "Change password")
    @PutMapping("/change-password")
    public ApiResponse<Void> changePassword(@RequestBody ChangePasswordRequest request) {
        userService.changePassword(request);

        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Password was changed successfully")
                .build();
    }
}