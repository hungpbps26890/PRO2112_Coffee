package com.poly.coffee.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {

    Long id;
    Integer quantity;
    Double price;
    DrinkResponse drink;
    SizeResponse size;
    List<ToppingResponse> toppings;
}

