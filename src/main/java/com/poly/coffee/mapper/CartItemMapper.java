package com.poly.coffee.mapper;

import com.poly.coffee.dto.request.CartItemRequest;
import com.poly.coffee.dto.response.CartItemResponse;
import com.poly.coffee.entity.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CartItemMapper {

    @Mapping(target = "toppings", ignore = true)
    CartItem toCartItem(CartItemRequest request);

    @Mapping(target = "toppings", ignore = true)
    void updateCartItem(@MappingTarget CartItem cartItem, CartItemRequest request);

    CartItemResponse toCartItemResponse(CartItem cartItem);
}
