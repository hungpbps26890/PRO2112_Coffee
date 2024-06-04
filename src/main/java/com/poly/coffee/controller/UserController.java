package com.poly.coffee.controller;

import com.poly.coffee.dto.request.UserCreationRequest;
import com.poly.coffee.dto.request.UserUpdateRequest;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.dto.response.UserResponse;
import com.poly.coffee.entity.User;
import com.poly.coffee.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/users")
public class UserController {

    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.createRequest(request));

        return apiResponse;
    }

    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .result(userService.getAllUsers())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<UserResponse> getUserById(@PathVariable Long id) {
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .result(userService.getUserById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable Long id,
            @RequestBody UserUpdateRequest request
    ) {
        ApiResponse<UserResponse> apiResponse = new ApiResponse<>();

        apiResponse.setResult(userService.updateUser(id,request));

        return apiResponse;
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("User was deleted successfully")
                .build();
    }
}