package com.poly.coffee.mapper;

import com.poly.coffee.dto.request.UserCreationRequest;
import com.poly.coffee.dto.request.UserUpdateRequest;
import com.poly.coffee.dto.response.UserResponse;
import com.poly.coffee.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
