package com.poly.coffee.controller;

import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.entity.PaymentMethod;
import com.poly.coffee.service.PaymentMethodService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/payment-methods")
public class PaymentMethodController {

    PaymentMethodService paymentMethodService;

    @GetMapping
    public ApiResponse<List<PaymentMethod>> getAllPaymentMethods() {
        return ApiResponse.<List<PaymentMethod>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Get all payment methods successfully")
                .result(paymentMethodService.getAllPaymentMethods())
                .build();
    }
}
