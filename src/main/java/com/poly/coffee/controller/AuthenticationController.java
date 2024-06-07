package com.poly.coffee.controller;

import com.nimbusds.jose.JOSEException;
import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.request.AuthenticationRequest;
import com.poly.coffee.dto.request.IntrospectRequest;
import com.poly.coffee.dto.request.LogoutRequest;
import com.poly.coffee.dto.request.RefreshRequest;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.dto.response.AuthenticationResponse;
import com.poly.coffee.dto.response.IntrospectResponse;
import com.poly.coffee.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody AuthenticationRequest request) {
        AuthenticationResponse result = authenticationService.authenticate(request);

        return ApiResponse.<AuthenticationResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        IntrospectResponse result = authenticationService.introspect(request);

        return ApiResponse.<IntrospectResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .result(result)
                .build();
    }
    
    @PostMapping("/logout")
    public ApiResponse<Void> logout(@RequestBody LogoutRequest request) throws ParseException, JOSEException {
        authenticationService.logout(request);

        return ApiResponse.<Void>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Logout successfully")
                .build();
    }

    @PostMapping("/refresh")
    public ApiResponse<AuthenticationResponse> refreshToken(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        AuthenticationResponse result = authenticationService.refreshToken(request);

        return ApiResponse.<AuthenticationResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .result(result)
                .build();
    }
}
