package com.poly.coffee.service;

import com.poly.coffee.dto.request.AuthenticationRequest;

public interface AuthenticationService {

    boolean authenticate(AuthenticationRequest request);
}
