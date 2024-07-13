package com.poly.coffee.service;

import com.poly.coffee.dto.response.VNPayResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface PaymentService {

    VNPayResponse createVNPayPayment(HttpServletRequest request);
}
