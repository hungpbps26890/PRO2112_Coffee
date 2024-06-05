package com.poly.coffee.controller;

import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.request.PermissionRequest;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.dto.response.PermissionResponse;
import com.poly.coffee.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/permissions")
public class PermissionController {

    PermissionService permissionService;

    @PostMapping
    public ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Create new permission successfully")
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Fetch all permissions successfully")
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    public ApiResponse<Void> deletePermission(@PathVariable String permission) {
        permissionService.deletePermission(permission);

        return ApiResponse.<Void>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Delete permission successfully")
                .build();
    }
}
