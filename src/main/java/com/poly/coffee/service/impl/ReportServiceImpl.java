package com.poly.coffee.service.impl;

import com.poly.coffee.entity.DrinkReport;
import com.poly.coffee.entity.RevenueReport;
import com.poly.coffee.repository.OrderItemRepository;
import com.poly.coffee.repository.OrderRepository;
import com.poly.coffee.service.ReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ReportServiceImpl implements ReportService {

    OrderItemRepository orderItemRepository;

    OrderRepository orderRepository;

    @Override
    public List<DrinkReport> getRevenueByDrink(String startDate, String endDate) {
        LocalDateTime startDatetime = convertStringToLocalDatetime(startDate);
        LocalDateTime endDatetime = convertStringToLocalDatetime(endDate);

        return orderItemRepository.revenueByDrink(startDatetime, endDatetime);
    }

    @Override
    public List<RevenueReport> getRevenueReport(String startDate, String endDate) {
        LocalDateTime startDatetime = convertStringToLocalDatetime(startDate);
        LocalDateTime endDatetime = convertStringToLocalDatetime(endDate);

        return orderRepository.getRevenueReport(startDatetime, endDatetime);
    }

    private LocalDateTime convertStringToLocalDatetime(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(dateString, formatter);
    }
}
