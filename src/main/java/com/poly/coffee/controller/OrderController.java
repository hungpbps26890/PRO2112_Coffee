package com.poly.coffee.controller;

import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.request.OrderRequest;
import com.poly.coffee.dto.request.UpdateOrderStatusRequest;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.dto.response.OrderResponse;
import com.poly.coffee.entity.Order;
import com.poly.coffee.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    OrderService orderService;

    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody OrderRequest request) {

        return ApiResponse.<OrderResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Create new order successfully")
                .result(orderService.createOrder(request))
                .build();
    }

    @GetMapping("/user")
    public ApiResponse<List<OrderResponse>> getOrdersByUser() {
        return ApiResponse.<List<OrderResponse>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Get orders by user successfully")
                .result(orderService.getOrdersByUser())
                .build();
    }

    @GetMapping
    public ApiResponse<List<OrderResponse>> getAllOrders() {
        return ApiResponse.<List<OrderResponse>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Get all orders successfully")
                .result(orderService.getAllOrders())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable Long id) {
        return  ApiResponse.<OrderResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Get order by id successfully")
                .result(orderService.getOrderById(id))
                .build();
    }

    @PutMapping("/order/update-status")
    public ApiResponse<OrderResponse> updateOrderStatus(@RequestBody UpdateOrderStatusRequest request) {
        return ApiResponse.<OrderResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Update order status by id successfully")
                .result(orderService.updateOrderStatus(request))
                .build();
    }
}
