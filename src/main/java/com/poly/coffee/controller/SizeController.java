package com.poly.coffee.controller;

import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.request.SizeRequest;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.dto.response.SizeResponse;
import com.poly.coffee.service.SizeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/sizes")
public class SizeController {

    SizeService sizeService;

    @PostMapping
    public ApiResponse<SizeResponse> createSize(@RequestBody SizeRequest request) {
        return ApiResponse.<SizeResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Create size successfully")
                .result(sizeService.createSize(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<SizeResponse>> getAllSizes() {
        return ApiResponse.<List<SizeResponse>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Get all sizes successfully")
                .result(sizeService.getAllSizes())
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<SizeResponse> getSizeById(@PathVariable Long id) {
        return ApiResponse.<SizeResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Get size successfully")
                .result(sizeService.getSizeById(id))
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<SizeResponse> updateSize(
            @PathVariable Long id,
            @RequestBody SizeRequest request
    ) {
        return ApiResponse.<SizeResponse>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Update size successfully")
                .result(sizeService.updateSize(id, request))
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteSize(@PathVariable Long id) {
        sizeService.deleteSize(id);
        return ApiResponse.<String>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Delete size successfully")
                .build();
    }
}
