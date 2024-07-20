package com.poly.coffee.service;

import com.poly.coffee.entity.DrinkReport;
import com.poly.coffee.entity.RevenueReport;

import java.util.List;

public interface ReportService {

    List<DrinkReport> getRevenueByDrink(String startDate, String endDate);

    List<RevenueReport> getRevenueReport(String startDate, String endDate);
}
