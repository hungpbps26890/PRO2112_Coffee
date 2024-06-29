package com.poly.coffee.controller;

import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.request.DrinkRequest;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.dto.response.DrinkResponse;
import com.poly.coffee.dto.response.PageResponse;
import com.poly.coffee.entity.Drink;
import com.poly.coffee.service.DrinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RestController
@RequestMapping("/api/drinks")
@Tag(name = "Drink Controller")
public class DrinkController {

    DrinkService drinkService;

    @Operation(summary = "Get all drinks")
    @GetMapping
    public ApiResponse<List<DrinkResponse>> getAllDrinks() {
        return ApiResponse.<List<DrinkResponse>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .result(drinkService.getAllDrinks())
                .build();
    }

    @Operation(summary = "Get a list of drinks with pagination and sort")
    @GetMapping("/pagination")
    public ApiResponse<PageResponse<?>> getDrinksPagination(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(5) @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(required = false) String sortBy
    ) {
        log.info("Request a list of drinks with pagination and sort");
        return ApiResponse.<PageResponse<?>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Get a list of drinks with pagination and sort")
                .result(drinkService.getDrinksPagination(pageNo, pageSize, sortBy))
                .build();
    }

    @Operation(summary = "Get a list of drinks with pagination, sort and search")
    @GetMapping("/search")
    public ApiResponse<PageResponse<?>> getDrinksSearch(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(5) @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String search
    ) {
        log.info("Request a list of drinks with pagination, sort and search");
        return ApiResponse.<PageResponse<?>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Get a list of drinks with pagination, sort and search")
                .result(drinkService.getDrinksSearch(pageNo, pageSize, sortBy, search))
                .build();
    }

    @Operation(summary = "Get a list of drinks with criteria")
    @GetMapping("/searchWithCriteria")
    public ApiResponse<PageResponse<?>> getDrinksWithCriteria(
            @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Min(5) @RequestParam(defaultValue = "10", required = false) int pageSize,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String categoryName,
            @RequestParam(required = false) String... search
    ) {
        log.info("Request a list of drinks with criteria");
        return ApiResponse.<PageResponse<?>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Get a list of drinks with criteria")
                .result(drinkService.getDrinksWithCriteria(pageNo, pageSize, sortBy, categoryName, search))
                .build();
    }

    @Operation(summary = "Get drink by id")
    @GetMapping("/{id}")
    public ApiResponse<DrinkResponse> getDrinkById(@PathVariable Long id) {
        return ApiResponse.<DrinkResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .result(drinkService.getDrinkById(id))
                .build();
    }

    @Operation(summary = "Create a new drink")
    @PostMapping
    public ApiResponse<DrinkResponse> createDrink(
            @RequestBody DrinkRequest request
    ) {
        return ApiResponse.<DrinkResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .result(drinkService.createDrink(request))
                .build();
    }

    @Operation(summary = "Update drink")
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

    @Operation(summary = "Delete drink by id")
    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteDrink(@PathVariable Long id) {
        drinkService.deleteDrink(id);
        return ApiResponse.<String>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Drink was deleted successfully")
                .build();
    }

    @Operation(summary = "Get drink by category")
    @GetMapping("/category/{id}")
    public ApiResponse<List<DrinkResponse>> getDrinksByCategoryId(@PathVariable Long id) {
        return ApiResponse.<List<DrinkResponse>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .result(drinkService.getDrinksByCategoryId(id))
                .build();
    }
}
