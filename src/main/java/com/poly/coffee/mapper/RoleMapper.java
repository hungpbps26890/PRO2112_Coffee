package com.poly.coffee.mapper;

import com.poly.coffee.dto.request.RoleRequest;
import com.poly.coffee.dto.response.RoleResponse;
import com.poly.coffee.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
