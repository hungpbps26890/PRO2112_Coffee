package com.poly.coffee.service;

import com.poly.coffee.entity.Drink;
import com.poly.coffee.entity.DrinkSize;

import java.util.List;


public interface DrinkSizeService {
    void deleteNotInUpdatedDrinkSizes(Drink drink, List<DrinkSize> updatedDrinkSizes);

}
