package com.poly.coffee.dto.request;

import com.poly.coffee.entity.DrinkSize;
import com.poly.coffee.entity.Topping;
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
    Double price;
    String description;
    Boolean isActive;
    List<String> images;
    Long categoryId;
    List<Topping> toppings;
    List<DrinkSize> drinkSizes;
}
