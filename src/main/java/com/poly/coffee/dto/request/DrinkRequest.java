package com.poly.coffee.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DrinkRequest {
    String name;
    Long price;
    String description;
    Boolean isActive;
    List<String> images;
    Long categoryId;
}
