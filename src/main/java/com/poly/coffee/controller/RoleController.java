package com.poly.coffee.controller;

import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.request.RoleRequest;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.dto.response.PermissionResponse;
import com.poly.coffee.dto.response.RoleResponse;
import com.poly.coffee.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    RoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Create new role successfully")
                .result(roleService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Fetch all roles successfully")
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    public ApiResponse<Void> deletePermission(@PathVariable String role) {
        roleService.deleteRole(role);

        return ApiResponse.<Void>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Delete role successfully")
                .build();
    }
}
