package com.poly.coffee.mapper;

import com.poly.coffee.dto.response.CartResponse;
import com.poly.coffee.entity.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {

    CartResponse toCartResponse(Cart cart);
}
