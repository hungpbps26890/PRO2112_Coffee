package com.poly.coffee.entity;

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
@Table(name = "drinks")
public class Drink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "nvarchar(255)")
    private String name;

    private Double price;

    @Column(columnDefinition = "nvarchar(MAX)")
    private String description;

    private Boolean isActive = true;

    @Column(length = 1000)
    @ElementCollection
    private List<String> images;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany()
    @JoinTable(name = "drink_toppings",
            joinColumns = @JoinColumn(name = "drink_id"),
            inverseJoinColumns = @JoinColumn(name = "topping_id")
    )
    private List<Topping> toppings = new ArrayList<>();

    @OneToMany(mappedBy = "drink", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DrinkSize> drinkSizes = new ArrayList<>();
}
