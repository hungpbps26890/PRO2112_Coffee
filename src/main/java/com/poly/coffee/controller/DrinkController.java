package com.poly.coffee.controller;

import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.request.DrinkRequest;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.dto.response.DrinkResponse;
import com.poly.coffee.entity.Drink;
import com.poly.coffee.service.DrinkService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/drinks")
public class DrinkController {

    DrinkService drinkService;

    @GetMapping
    public ApiResponse<List<DrinkResponse>> getAllDrinks() {
        return ApiResponse.<List<DrinkResponse>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .result(drinkService.getAllDrinks())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<DrinkResponse> getDrinkById(@PathVariable Long id) {
        return ApiResponse.<DrinkResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .result(drinkService.getDrinkById(id))
                .build();
    }

    @GetMapping("/drink/{id}")
    public ApiResponse<Drink> findDrinkById(@PathVariable Long id) {
        return ApiResponse.<Drink>builder()
                .code(StatusCode.SUCCESS_CODE)
                .result(drinkService.findDrinkById(id))
                .build();
    }

    @PostMapping
    public ApiResponse<DrinkResponse> createDrink(
            @RequestBody DrinkRequest request
    ) {
        return ApiResponse.<DrinkResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .result(drinkService.createDrink(request))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<DrinkResponse> updateDrink(
            @PathVariable Long id,
            @RequestBody DrinkRequest request
    ) {
        return ApiResponse.<DrinkResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .result(drinkService.updateDrink(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDrin(@PathVariable Long id) {
        drinkService.deleteDrink(id);
        return ApiResponse.<String>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Drink was deleted successfully")
                .build();
    }

    @GetMapping("/category/{id}")
    public ApiResponse<List<DrinkResponse>> getDrinksByCategoryId(@PathVariable Long id) {
        return ApiResponse.<List<DrinkResponse>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .result(drinkService.getDrinksByCategoryId(id))
                .build();
    }
}
