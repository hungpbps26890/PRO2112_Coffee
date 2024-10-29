package com.poly.coffee.service.impl;

import com.poly.coffee.dto.request.OrderRequest;
import com.poly.coffee.dto.request.UpdateOrderStatusRequest;
import com.poly.coffee.dto.response.OrderResponse;
import com.poly.coffee.dto.response.PageResponse;
import com.poly.coffee.entity.*;
import com.poly.coffee.exception.AppException;
import com.poly.coffee.exception.ErrorCode;
import com.poly.coffee.mapper.OrderMapper;
import com.poly.coffee.repository.*;
import com.poly.coffee.service.CartService;
import com.poly.coffee.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    OrderItemRepository orderItemRepository;

    AddressRepository addressRepository;

    PaymentMethodRepository paymentMethodRepository;

    OrderStatusRepository orderStatusRepository;

    UserRepository userRepository;

    CartRepository cartRepository;

    OrderMapper orderMapper;

    CartService cartService;

    SearchRepository searchRepository;

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        User user = findUser();

        Cart cart = cartRepository.findByUserId(user.getId());

        Order order = new Order();

        if (request.getAddress() != null) {
            Address savedAddress = addressRepository.save(request.getAddress());
            order.setAddress(savedAddress);
        }


        PaymentMethod paymentMethod = paymentMethodRepository.findById(request.getPaymentMethod().getId())
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_METHOD_NOT_FOUND));

        order.setUser(user);
        order.setCreateDate(LocalDateTime.now());
        order.setOrderStatus(orderStatusRepository.findById(1L).orElse(null));
        order.setTotalItems(cart.getTotalItems());
        order.setTotalPrice(cart.getTotalPrice());
        order.setPaymentMethod(paymentMethod);

        Order createdOrder = orderRepository.save(order);

        List<OrderItem> orderItems = new ArrayList<>();

        Set<CartItem> cartItems = cart.getCartItems();

        for (CartItem cartItem : cartItems) {
            List<Topping> toppings = cartItem.getToppings();

            OrderItem orderItem = OrderItem.builder()
                    .order(createdOrder)
                    .drink(cartItem.getDrink())
                    .price(cartItem.getPrice())
                    .quantity(cartItem.getQuantity())
                    .size(cartItem.getSize())
                    .toppings(new ArrayList<>(toppings))
                    .build();

            OrderItem savedOrderItem = orderItemRepository.save(orderItem);

            orderItems.add(savedOrderItem);
        }

        createdOrder.setOrderItems(orderItems);

        Order savedOrder = orderRepository.save(createdOrder);

        user.getOrders().add(savedOrder);

        cartService.clearCart();

        return orderMapper.toOrderResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getOrdersByUser() {
        User user = findUser();
        List<Order> orders = orderRepository.findByUserId(user.getId());
        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(orderMapper::toOrderResponse).toList();
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_NOT_FOUND));
        return orderMapper.toOrderResponse(order);
    }

    @Override
    public OrderResponse updateOrderStatus(UpdateOrderStatusRequest request) {
        Order order = orderRepository.findById(request.getId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_NOT_FOUND));

        OrderStatus orderStatus = orderStatusRepository.findById(request.getOrderStatus().getId())
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_NOT_FOUND));

        order.setPaymentStatus(request.getPaymentStatus());

        order.setOrderStatus(orderStatus);

        return orderMapper.toOrderResponse(orderRepository.save(order));
    }

    @Override
    public PageResponse<?> getOrdersWithCriteria(int pageNo, int pageSize, String... search) {
        return searchRepository.advancedSearchOrdersWithCriteria(pageNo, pageSize, search);
    }

    private User findUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
    }
}
