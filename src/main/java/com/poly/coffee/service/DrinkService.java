package com.poly.coffee.service;

import com.poly.coffee.dto.request.DrinkRequest;
import com.poly.coffee.dto.response.DrinkResponse;
import com.poly.coffee.dto.response.PageResponse;
import com.poly.coffee.entity.Drink;

import java.util.List;

public interface DrinkService {

    DrinkResponse createDrink(DrinkRequest request);

    List<DrinkResponse> getAllDrinks();

    PageResponse<?> getDrinksPagination(int pageNo, int pageSize, String sortBy);

    PageResponse<?> getDrinksSearch(int pageNo, int pageSize, String sortBy, String search);

    PageResponse<?> getDrinksWithCriteria(int pageNo, int pageSize, String sortBy, String categoryName, String... search);

    DrinkResponse getDrinkById(Long id);

    DrinkResponse updateDrink(Long id, DrinkRequest request);

    void deleteDrink(Long id);

    List<DrinkResponse> getDrinksByCategoryId(Long categoryId);

    Drink findDrinkById(Long id);
}
