package com.poly.coffee.repository;

import com.poly.coffee.entity.Order;
import com.poly.coffee.entity.RevenueReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    @Query("SELECT new RevenueReport(CAST(o.createDate AS date), SUM(o.totalPrice), COUNT(o)) FROM Order o WHERE o.createDate BETWEEN ?1 AND ?2 GROUP BY CAST(o.createDate AS date)")
    List<RevenueReport> getRevenueReport(LocalDateTime startDatetime, LocalDateTime endDatetime);
}
