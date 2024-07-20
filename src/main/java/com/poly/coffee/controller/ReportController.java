package com.poly.coffee.controller;

import com.poly.coffee.constant.StatusCode;
import com.poly.coffee.dto.response.ApiResponse;
import com.poly.coffee.entity.DrinkReport;
import com.poly.coffee.entity.RevenueReport;
import com.poly.coffee.service.ReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/reports")
public class ReportController {

    ReportService reportService;

    @GetMapping("/drinks")
    public ApiResponse<List<DrinkReport>> getRevenueByDrink(
            @RequestParam String startDate,
            @RequestParam String endDate)
    {
        return ApiResponse.<List<DrinkReport>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Get revenue by drink successfully")
                .result(reportService.getRevenueByDrink(startDate, endDate))
                .build();
    }

    @GetMapping("/revenue")
    public ApiResponse<List<RevenueReport>> getRevenue(
            @RequestParam String startDate,
            @RequestParam String endDate)
    {
        return ApiResponse.<List<RevenueReport>>builder()
                .code(StatusCode.SUCCESS_CODE)
                .message("Get revenue successfully")
                .result(reportService.getRevenueReport(startDate, endDate))
                .build();
    }
}
