package com.poly.coffee.dto.request;

import com.poly.coffee.entity.OrderStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateOrderStatusRequest {
   Long id;
   OrderStatus orderStatus;
   Boolean paymentStatus;
}
