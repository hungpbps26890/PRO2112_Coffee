package com.poly.coffee.service;

import com.poly.coffee.dto.request.PermissionRequest;
import com.poly.coffee.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {

    PermissionResponse create (PermissionRequest request);

    List<PermissionResponse> getAll();

    void deletePermission(String permission);
}
