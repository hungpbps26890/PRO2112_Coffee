package com.poly.coffee.repository;

import com.poly.coffee.entity.DrinkReport;
import com.poly.coffee.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    @Query("SELECT new DrinkReport(o.drink, SUM(o.price * o.quantity), SUM(o.quantity)) FROM OrderItem o WHERE o.order.createDate BETWEEN ?1 AND ?2 GROUP BY o.drink")
    List<DrinkReport> revenueByDrink(LocalDateTime startDate, LocalDateTime endDate);
}
