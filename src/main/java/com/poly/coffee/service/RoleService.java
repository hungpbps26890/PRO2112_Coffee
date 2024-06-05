package com.poly.coffee.service;

import com.poly.coffee.dto.request.RoleRequest;
import com.poly.coffee.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {

    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAll();

    void deleteRole(String role);
}
