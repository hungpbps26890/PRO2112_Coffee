package com.poly.coffee.service.impl;

import com.poly.coffee.dto.request.UserCreationRequest;
import com.poly.coffee.dto.request.UserUpdateMyInfoRequest;
import com.poly.coffee.dto.request.UserUpdateRequest;
import com.poly.coffee.dto.response.UserResponse;
import com.poly.coffee.entity.Role;
import com.poly.coffee.entity.User;
import com.poly.coffee.enums.RoleEnum;
import com.poly.coffee.exception.AppException;
import com.poly.coffee.exception.ErrorCode;
import com.poly.coffee.mapper.UserMapper;
import com.poly.coffee.repository.RoleRepository;
import com.poly.coffee.repository.UserRepository;
import com.poly.coffee.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    RoleRepository roleRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    @Override
    public UserResponse createRequest(UserCreationRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(request.getPassword()));

        HashSet<Role> roles = new HashSet<>();

        Role roleUser = roleRepository.findById(RoleEnum.USER.name())
                .orElseThrow(() -> new AppException(ErrorCode.ROLE_NOT_FOUND));

        roles.add(roleUser);
        user.setRoles(roles);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public List<UserResponse> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toList());
    }

    @PostAuthorize("returnObject.username == authentication.name")
    @Override
    public UserResponse getUserById(Long id) {
        return userMapper.toUserResponse(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    @Override
    public UserResponse getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserResponse(user);
    }

    @PreAuthorize("hasAuthority('UPDATE_DATA')")
    @Override
    public UserResponse updateUser(Long id, UserUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

       userMapper.updateUser(user, request);

       user.setPassword(passwordEncoder.encode(request.getPassword()));

       List<Role> roles = roleRepository.findAllById(request.getRoles());
       user.setRoles(new HashSet<>(roles));

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse updateMyInfo(UserUpdateMyInfoRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        User user = userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        userMapper.updateMyInfo(user, request);

        return userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));

        userRepository.deleteById(id);
    }
}