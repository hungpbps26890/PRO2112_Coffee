package com.poly.coffee.dto.response;

import com.poly.coffee.entity.Category;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DrinkResponse {
    Long id;
    String name;
    Long price;
    String description;
    Boolean isActive;
    List<String> images;
    Category category;
}
