package com.poly.coffee.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DrinkReport implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Transient
    private Drink drink;
    private Double revenue;
    private Long quantity;

    public DrinkReport(Drink drink, Double revenue, Long quantity) {
        this.drink = drink;
        this.revenue = revenue;
        this.quantity = quantity;
    }
}
