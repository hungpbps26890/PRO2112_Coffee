package com.poly.coffee.controller;

import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.entity.OrderStatus;
import com.poly.coffee.service.OrderStatusService;
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
@RequestMapping("/api/order-statuses")
public class OrderStatusController {

    OrderStatusService orderStatusService;

    @GetMapping
    public ApiResponse<List<OrderStatus>> getAllOrderStatus() {
        return ApiResponse.<List<OrderStatus>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Get all order status successfully")
                .result(orderStatusService.getAllOrderStatus())
                .build();
    }
}
