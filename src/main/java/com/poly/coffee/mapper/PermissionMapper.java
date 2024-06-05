package com.poly.coffee.mapper;

import com.poly.coffee.dto.request.PermissionRequest;
import com.poly.coffee.dto.response.PermissionResponse;
import com.poly.coffee.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PermissionMapper {

    Permission toPermission(PermissionRequest request);

    void updatePermission(@MappingTarget Permission permission, PermissionRequest request);

    PermissionResponse toPermissionResponse(Permission permission);

}
