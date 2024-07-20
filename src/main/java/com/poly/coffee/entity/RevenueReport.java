package com.poly.coffee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RevenueReport implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Date date;
    private Double revenue;
    private Long numberOfOrders;

    public RevenueReport(Date date, Double revenue, Long numberOfOrders) {
        this.date = date;
        this.revenue = revenue;
        this.numberOfOrders = numberOfOrders;
    }
}
