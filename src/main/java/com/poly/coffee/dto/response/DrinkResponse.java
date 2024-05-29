package com.poly.coffee.dto.response;

import com.poly.coffee.entity.Category;
import com.poly.coffee.entity.DrinkSize;
import com.poly.coffee.entity.Topping;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DrinkResponse {
    Long id;
    String name;
    Double price;
    String description;
    Boolean isActive;
    List<String> images;
    Category category;
    List<Topping> toppings;
    List<DrinkSize> drinkSizes;
}
