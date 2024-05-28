package com.poly.coffee.controller;

import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.request.ToppingRequest;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.dto.response.ToppingResponse;
import com.poly.coffee.service.ToppingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/toppings")
public class ToppingController {

    ToppingService toppingService;

    @PostMapping
    public ApiResponse<ToppingResponse> createTopping(@RequestBody ToppingRequest request) {
        return ApiResponse.<ToppingResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Create topping successfully")
                .result(toppingService.createTopping(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<ToppingResponse>> getAllToppings() {
        return ApiResponse.<List<ToppingResponse>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Fetch all toppings successfully")
                .result(toppingService.getAllToppings())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<ToppingResponse> getToppingById(@PathVariable Long id) {
        return ApiResponse.<ToppingResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Fetch topping successfully")
                .result(toppingService.getToppingById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ToppingResponse> updateTopping(
            @PathVariable Long id,
            @RequestBody ToppingRequest request
    ) {
        return ApiResponse.<ToppingResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Update topping successfully")
                .result(toppingService.updateTopping(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteTopping(@PathVariable Long id) {
        toppingService.deleteTopping(id);

        return ApiResponse.<String>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Delete topping successfully")
                .build();
    }
}
