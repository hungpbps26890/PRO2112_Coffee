package com.poly.coffee.controller;

import com.nimbusds.jose.JOSEException;
import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.request.AuthenticationRequest;
import com.poly.coffee.dto.request.IntrospectRequest;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.dto.response.AuthenticationResponse;
import com.poly.coffee.dto.response.IntrospectResponse;
import com.poly.coffee.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    AuthenticationService authenticationService;

    @PostMapping("/log-in")
    public ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
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
}
