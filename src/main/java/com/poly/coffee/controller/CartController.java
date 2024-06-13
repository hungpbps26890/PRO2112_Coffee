package com.poly.coffee.controller;

import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.request.CartItemRequest;
import com.poly.coffee.dto.request.CartItemUpdateQuantityRequest;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.dto.response.CartItemResponse;
import com.poly.coffee.dto.response.CartResponse;
import com.poly.coffee.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/carts")
public class CartController {

    CartService cartService;

    @PutMapping("/cart-item/add")
    public ApiResponse<CartItemResponse> addItemToCart(@RequestBody CartItemRequest request) {
        return ApiResponse.<CartItemResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Add new item to cart successfully")
                .result(cartService.addItemToCart(request))
                .build();
    }

    @PutMapping("/cart-item/update-quantity")
    public ApiResponse<CartItemResponse> updateItemQuantity(@RequestBody CartItemUpdateQuantityRequest request) {
        return ApiResponse.<CartItemResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Update item quantity successfully")
                .result(cartService.updateItemQuantity(request.getId(), request.getQuantity()))
                .build();
    }

    @DeleteMapping("/cart-item/{cartItemId}")
    public ApiResponse<Object> removeItemFromCart(@PathVariable Long cartItemId) {
        cartService.removeItemFromCart(cartItemId);

        return ApiResponse.builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Remove item from cart successfully")
                .build();
    }

    @GetMapping("/cart")
    public ApiResponse<CartResponse> getCartByUser() {
        return ApiResponse.<CartResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Fetch cart by user successfully")
                .result(cartService.findCartByUser())
                .build();
    }

    @PutMapping("/cart/clear")
    public ApiResponse<CartResponse> clearCart() {
        return ApiResponse.<CartResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Clear cart successfully")
                .result(cartService.clearCart())
                .build();
    }
}
