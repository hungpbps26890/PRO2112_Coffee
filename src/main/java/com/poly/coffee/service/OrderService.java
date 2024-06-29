package com.poly.coffee.service;

import com.poly.coffee.dto.request.OrderRequest;
import com.poly.coffee.dto.request.UpdateOrderStatusRequest;
import com.poly.coffee.dto.response.OrderResponse;
import com.poly.coffee.dto.response.PageResponse;
import com.poly.coffee.entity.Order;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> getOrdersByUser();

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);

    OrderResponse updateOrderStatus(UpdateOrderStatusRequest request);

    PageResponse<?> getOrdersWithCriteria(int pageNo, int pageSize, String... search);
}
