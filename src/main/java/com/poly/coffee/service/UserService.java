package com.poly.coffee.service;

import com.poly.coffee.dto.request.UserCreationRequest;
import com.poly.coffee.dto.request.UserUpdateMyInfoRequest;
import com.poly.coffee.dto.request.UserUpdateRequest;
import com.poly.coffee.dto.response.UserResponse;
import com.poly.coffee.entity.User;

import java.util.List;

public interface UserService {

    UserResponse createRequest(UserCreationRequest request);

    List<UserResponse> getAllUsers();

    UserResponse getUserById(Long id);

    UserResponse getMyInfo();

    UserResponse updateUser(Long id, UserUpdateRequest request);

    UserResponse updateMyInfo(UserUpdateMyInfoRequest request);

    void deleteUser(Long id);
}