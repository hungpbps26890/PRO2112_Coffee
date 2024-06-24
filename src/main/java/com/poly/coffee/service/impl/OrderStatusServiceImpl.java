package com.poly.coffee.service.impl;

import com.poly.coffee.entity.OrderStatus;
import com.poly.coffee.repository.OrderStatusRepository;
import com.poly.coffee.service.OrderStatusService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    OrderStatusRepository orderStatusRepository;
    @Override
    public List<OrderStatus> getAllOrderStatus() {
        return orderStatusRepository.findAll();
    }
}
