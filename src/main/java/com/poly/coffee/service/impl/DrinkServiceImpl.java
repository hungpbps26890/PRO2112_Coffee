package com.poly.coffee.service.impl;

import com.poly.coffee.dto.request.DrinkRequest;
import com.poly.coffee.dto.response.DrinkResponse;
import com.poly.coffee.entity.Category;
import com.poly.coffee.entity.Drink;
import com.poly.coffee.exception.AppException;
import com.poly.coffee.exception.ErrorCode;
import com.poly.coffee.mapper.CategoryMapper;
import com.poly.coffee.mapper.DrinkMapper;
import com.poly.coffee.repository.CategoryRepository;
import com.poly.coffee.repository.DrinkRepository;
import com.poly.coffee.service.CategoryService;
import com.poly.coffee.service.DrinkService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class DrinkServiceImpl implements DrinkService {

    DrinkRepository drinkRepository;

    DrinkMapper drinkMapper;

    CategoryRepository categoryRepository;

    @Override
    public DrinkResponse createDrink(DrinkRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Drink drink = drinkMapper.toDrink(request);

        drink.setCategory(category);

        return drinkMapper.toDrinkResponse(drinkRepository.save(drink));
    }

    @Override
    public List<DrinkResponse> getAllDrinks() {

        return drinkRepository.findAll()
                .stream()
                .map(drinkMapper::toDrinkResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DrinkResponse getDrinkById(Long id) {
        Drink drink = findDrinkById(id);
        return drinkMapper.toDrinkResponse(drink);
    }

    @Override
    public DrinkResponse updateDrink(Long id, DrinkRequest request) {
        Drink drink = findDrinkById(id);

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        drinkMapper.updateDrink(drink, request);

        drink.setCategory(category);

        return drinkMapper.toDrinkResponse(drinkRepository.save(drink));
    }

    @Override
    public void deleteDrink(Long id) {
        Drink drink = findDrinkById(id);
        drinkRepository.deleteById(id);
    }

    @Override
    public List<DrinkResponse> getDrinksByCategoryId(Long categoryId) {
        return drinkRepository.findByCategoryId(categoryId)
                .stream()
                .map(drinkMapper::toDrinkResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Drink findDrinkById(Long id) {
        return drinkRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.DRINK_NOT_FOUND));
    }
}
