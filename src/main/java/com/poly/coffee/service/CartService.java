package com.poly.coffee.service;

import com.poly.coffee.dto.request.CartItemRequest;
import com.poly.coffee.dto.response.CartItemResponse;
import com.poly.coffee.dto.response.CartResponse;

public interface CartService {

    CartItemResponse addItemToCart(CartItemRequest request);

    CartItemResponse updateItemQuantity(Long id, Integer quantity);

    void removeItemFromCart(Long cartItemId);

    CartResponse findCartByUser();

    CartResponse clearCart();

}
