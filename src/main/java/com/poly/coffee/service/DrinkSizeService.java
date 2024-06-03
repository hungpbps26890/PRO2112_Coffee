package com.poly.coffee.service;

import com.poly.coffee.entity.Drink;
import com.poly.coffee.entity.DrinkSize;
import com.poly.coffee.repository.DrinkSizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DrinkSizeService {

    @Autowired
    private DrinkSizeRepository drinkSizeRepository;

    public void deleteNotInUpdatedDrinkSizes(Drink drink, List<DrinkSize> updatedDrinkSizes) {
        List<DrinkSize> drinkSizes = drinkSizeRepository.findByDrinkId(drink.getId());
        List<DrinkSize> deleteDrinkSizes = drinkSizes.stream().filter(drinkSize -> !updatedDrinkSizes.contains(drinkSize)).toList();

        drinkSizeRepository.deleteAllByIdInBatch(deleteDrinkSizes.stream()
                .map(DrinkSize::getId)
                .collect(Collectors.toList()));
    }
}
