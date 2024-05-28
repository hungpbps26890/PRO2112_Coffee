package com.poly.coffee.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ToppingResponse {
    Long id;
    String name;
    Long price;
    Boolean isActive;
}
