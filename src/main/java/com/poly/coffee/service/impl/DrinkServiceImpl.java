package com.poly.coffee.service.impl;

import com.poly.coffee.dto.request.DrinkRequest;
import com.poly.coffee.dto.response.DrinkResponse;
import com.poly.coffee.entity.Category;
import com.poly.coffee.entity.Drink;
import com.poly.coffee.entity.DrinkSize;
import com.poly.coffee.entity.Size;
import com.poly.coffee.exception.AppException;
import com.poly.coffee.exception.ErrorCode;
import com.poly.coffee.mapper.DrinkMapper;
import com.poly.coffee.model.DrinkSizeKey;
import com.poly.coffee.repository.CategoryRepository;
import com.poly.coffee.repository.DrinkRepository;
import com.poly.coffee.repository.DrinkSizeRepository;
import com.poly.coffee.repository.SizeRepository;
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

    SizeRepository sizeRepository;

    DrinkSizeRepository drinkSizeRepository;

    @Override
    public DrinkResponse createDrink(DrinkRequest request) {
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        Drink drink = drinkMapper.toDrink(request);

        drink.setCategory(category);

        Drink newDrink = drinkRepository.save(drink);

        newDrink.getDrinkSizes().addAll(
                request
                  .getDrinkSizes()
                  .stream()
                  .map(drinkSize -> {
                      Size size = sizeRepository.findById(drinkSize.getSize().getId())
                              .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));

                      DrinkSize newDrinkSize = new DrinkSize();

                      newDrinkSize.setId(new DrinkSizeKey(newDrink.getId(), size.getId()));
                      newDrinkSize.setDrink(newDrink);
                      newDrinkSize.setSize(size);
                      newDrinkSize.setPrice(drinkSize.getPrice());

                      return drinkSizeRepository.save(newDrinkSize);
                  }).toList()
        );

        return drinkMapper.toDrinkResponse(drinkRepository.save(newDrink));
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

        Drink updatedDrink = drinkRepository.save(drink);

        updatedDrink.getDrinkSizes().addAll(
                request
                        .getDrinkSizes()
                        .stream()
                        .map(drinkSize -> {
                            Size size = sizeRepository.findById(drinkSize.getSize().getId())
                                    .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));

                            DrinkSize newDrinkSize = new DrinkSize();

                            newDrinkSize.setId(new DrinkSizeKey(updatedDrink.getId(), size.getId()));
                            newDrinkSize.setDrink(updatedDrink);
                            newDrinkSize.setSize(size);
                            newDrinkSize.setPrice(drinkSize.getPrice());

                            return drinkSizeRepository.save(newDrinkSize);
                        }).toList()
        );

        return drinkMapper.toDrinkResponse(drinkRepository.save(updatedDrink));
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
