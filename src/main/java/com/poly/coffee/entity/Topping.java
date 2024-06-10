package com.poly.coffee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "toppings")
public class Topping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    private Double price;

    private Boolean isActive;

    @JsonIgnore
    @ManyToMany(mappedBy = "toppings")
    private List<Drink> drinks = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "toppings")
    private List<CartItem> cartItems = new ArrayList<>();
}
