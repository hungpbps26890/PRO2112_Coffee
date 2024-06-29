package com.poly.coffee.service.impl;

import com.poly.coffee.dto.request.DrinkRequest;
import com.poly.coffee.dto.response.DrinkResponse;
import com.poly.coffee.dto.response.PageResponse;
import com.poly.coffee.entity.Category;
import com.poly.coffee.entity.Drink;
import com.poly.coffee.entity.DrinkSize;
import com.poly.coffee.entity.Size;
import com.poly.coffee.exception.AppException;
import com.poly.coffee.exception.ErrorCode;
import com.poly.coffee.mapper.DrinkMapper;
import com.poly.coffee.model.DrinkSizeKey;
import com.poly.coffee.repository.*;
import com.poly.coffee.service.DrinkService;
import com.poly.coffee.service.DrinkSizeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    DrinkSizeService drinkSizeService;

    SearchRepository searchRepository;

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
    public PageResponse<?> getDrinksPagination(int pageNo, int pageSize, String sortBy) {

        List<Sort.Order> sorts = new ArrayList<>();

        if (StringUtils.hasLength(sortBy)) {
            // regex: firstName:asc|desc
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");
            Matcher matcher = pattern.matcher(sortBy);
            if (matcher.find()) {
                if (matcher.group(3).equalsIgnoreCase("desc")) {
                    sorts.add(new Sort.Order(Sort.Direction.DESC, matcher.group(1)));
                } else {
                    sorts.add(new Sort.Order(Sort.Direction.ASC, matcher.group(1)));
                }
            }
        }

        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sorts));

        Page<Drink> drinks = drinkRepository.findAll(pageable);

        List<DrinkResponse> drinkResponses = drinks.stream()
                .map(drinkMapper::toDrinkResponse)
                .toList();

        return PageResponse.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalPages(drinks.getTotalPages())
                .totalElements(drinks.getTotalElements())
                .items(drinkResponses)
                .build();
    }

    @Override
    public PageResponse<?> getDrinksSearch(int pageNo, int pageSize, String sortBy, String search) {
        return searchRepository.getDrinksSearch(pageNo, pageSize, sortBy, search);
    }

    @Override
    public PageResponse<?> getDrinksWithCriteria(int pageNo, int pageSize, String sortBy, String categoryName, String... search) {
        return searchRepository.advancedSearchDrinksWithCriteria(pageNo, pageSize, sortBy, categoryName, search);
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

        List<DrinkSize> updatedDrinkSizes = request.getDrinkSizes()
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
                }).toList();

        drinkSizeService.deleteNotInUpdatedDrinkSizes(updatedDrink, updatedDrinkSizes);

        updatedDrink.getDrinkSizes().addAll(updatedDrinkSizes);

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
