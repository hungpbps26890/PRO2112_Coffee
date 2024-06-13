package com.poly.coffee.service.impl;

import com.poly.coffee.dto.request.CartItemRequest;
import com.poly.coffee.dto.response.CartItemResponse;
import com.poly.coffee.dto.response.CartResponse;
import com.poly.coffee.entity.*;
import com.poly.coffee.exception.AppException;
import com.poly.coffee.exception.ErrorCode;
import com.poly.coffee.mapper.CartItemMapper;
import com.poly.coffee.mapper.CartMapper;
import com.poly.coffee.model.DrinkSizeKey;
import com.poly.coffee.repository.*;
import com.poly.coffee.service.CartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class CartServiceImpl implements CartService {

    CartRepository cartRepository;

    CartItemRepository cartItemRepository;

    DrinkRepository drinkRepository;

    UserRepository userRepository;

    CartItemMapper cartItemMapper;

    CartMapper cartMapper;

    SizeRepository sizeRepository;

    ToppingRepository toppingRepository;

    DrinkSizeRepository drinkSizeRepository;

    @Override
    public CartItemResponse addItemToCart(CartItemRequest request) {
        User user = findUser();

        Cart cart = cartRepository.findByUserId(user.getId());

        Drink drink = drinkRepository.findById(request.getDrinkId())
                .orElseThrow(() -> new AppException(ErrorCode.DRINK_NOT_FOUND));

        Double price = drink.getPrice();

        Size size = null;
        if (request.getSizeId() != null) {
            size = sizeRepository.findById(request.getSizeId())
                    .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));

            DrinkSize drinkSize = drinkSizeRepository.getReferenceById(new DrinkSizeKey(drink.getId(), size.getId()));
            price = drinkSize.getPrice();
        }

        List<Topping> toppings = toppingRepository.findAllById(request
                .getToppings());

        if (!toppings.isEmpty()) {
            Double toppingsPrice = toppings.stream()
                    .map(Topping::getPrice)
                    .reduce(Double::sum).get();

            price += toppingsPrice;
        }

        CartItem newCartItem = CartItem.builder()
                .drink(drink)
                .cart(cart)
                .size(size)
                .toppings(toppings)
                .quantity(request.getQuantity())
                .price(request.getQuantity() * price)
                .build();

        for (CartItem item : cart.getCartItems()) {
            if (item.getDrink().equals(drink)) {
                int newQuantity = item.getQuantity() + request.getQuantity();

                item.setSize(newCartItem.getSize());
                item.setToppings(newCartItem.getToppings());
                item.setQuantity(newQuantity);
                item.setPrice(newQuantity * price);

                CartItem updatedCartItem = cartItemRepository.save(item);

                updateCart(cart);

                return cartItemMapper.toCartItemResponse(updatedCartItem);
            }
        }

        CartItem savedCartItem = cartItemRepository.save(newCartItem);

        cart.getCartItems().add(savedCartItem);

        updateCart(cart);

        return cartItemMapper.toCartItemResponse(savedCartItem);
    }

    @Override
    public CartItemResponse updateItemQuantity(Long id, Integer quantity) {
        User user = findUser();

        Cart cart = cartRepository.findByUserId(user.getId());

        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        Double pricePerItem = cartItem.getPrice() / cartItem.getQuantity();

        cartItem.setQuantity(quantity);
        cartItem.setPrice(pricePerItem * quantity);

        CartItem updatedCartItem = cartItemRepository.save(cartItem);

        updateCart(cart);

        return cartItemMapper.toCartItemResponse(updatedCartItem);
    }

    @Override
    public void removeItemFromCart(Long cartItemId) {
        User user = findUser();

        Cart cart = cartRepository.findByUserId(user.getId());

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_ITEM_NOT_FOUND));

        cart.getCartItems().remove(cartItem);

        updateCart(cart);
    }

    @Override
    public CartResponse findCartByUser() {
        User user = findUser();

        Cart cart = cartRepository.findByUserId(user.getId());

        return cartMapper.toCartResponse(cart);
    }

    @Override
    public CartResponse clearCart() {
        User user = findUser();

        Cart cart = cartRepository.findByUserId(user.getId());

        cart.getCartItems().clear();
        cart.setTotalPrice(0d);
        cart.setTotalItems(0);

        return cartMapper.toCartResponse(cartRepository.save(cart));
    }

    private User findUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        return userRepository.findByUsername(name)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }

    private void updateCart(Cart cart) {
        Integer totalItems = cart.getCartItems().stream()
                .map(CartItem::getQuantity)
                .reduce(Integer::sum).orElse(0);

        Double totalPrice = cart.getCartItems().stream()
                .map(CartItem::getPrice)
                .reduce(Double::sum).orElse(0d);

        cart.setTotalItems(totalItems);
        cart.setTotalPrice(totalPrice);

        cartRepository.save(cart);
    }
}
