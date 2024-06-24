package com.poly.coffee.mapper;

import com.poly.coffee.dto.response.OrderResponse;
import com.poly.coffee.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toOrderResponse(Order order);
}
