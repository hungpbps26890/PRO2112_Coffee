package com.poly.coffee.service;

import com.nimbusds.jose.JOSEException;
import com.poly.coffee.dto.request.AuthenticationRequest;
import com.poly.coffee.dto.request.IntrospectRequest;
import com.poly.coffee.dto.request.LogoutRequest;
import com.poly.coffee.dto.request.RefreshRequest;
import com.poly.coffee.dto.response.AuthenticationResponse;
import com.poly.coffee.dto.response.IntrospectResponse;
import org.springframework.security.core.Authentication;

import java.text.ParseException;

public interface AuthenticationService {

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse authenticateOAuth2(Authentication authentication);

    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    AuthenticationResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;
}
