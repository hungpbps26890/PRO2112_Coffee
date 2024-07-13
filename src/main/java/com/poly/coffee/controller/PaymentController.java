package com.poly.coffee.controller;

import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.request.UpdateOrderStatusRequest;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.dto.response.VNPayResponse;
import com.poly.coffee.entity.OrderStatus;
import com.poly.coffee.service.OrderService;
import com.poly.coffee.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    PaymentService paymentService;

    OrderService orderService;

    @GetMapping("/vn-pay")
    public ApiResponse<VNPayResponse> pay(HttpServletRequest request) {
        return ApiResponse.<VNPayResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("VNPay Payment Process Successfully")
                .result(paymentService.createVNPayPayment(request))
                .build();
    }

    @GetMapping("/vn-pay-callback")
    public void payCallbackHandler(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String status = request.getParameter("vnp_ResponseCode");
        long orderId = Integer.parseInt(request.getParameter("orderId"));

        if (status.equals("00")) {
            UpdateOrderStatusRequest updateOrderStatusRequest = UpdateOrderStatusRequest.builder()
                    .id(orderId)
                    .orderStatus(OrderStatus.builder().id(1L).build())
                    .paymentStatus(true).build();
            orderService.updateOrderStatus(updateOrderStatusRequest);

            response.sendRedirect("http://localhost:2000/order");
        } else {
            response.sendRedirect("http://localhost:2000/order");
        }
    }
}
