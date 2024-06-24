package com.poly.coffee.dto.response;

import com.poly.coffee.entity.Address;
import com.poly.coffee.entity.OrderItem;
import com.poly.coffee.entity.OrderStatus;
import com.poly.coffee.entity.PaymentMethod;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long id;
    Integer totalItems;
    Double totalPrice;
    LocalDateTime createDate;
    Boolean paymentStatus;
    List<OrderItem> orderItems;
    UserResponse user;
    OrderStatus orderStatus;
    PaymentMethod paymentMethod;
    Address address;
}
