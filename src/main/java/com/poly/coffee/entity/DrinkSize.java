package com.poly.coffee.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.poly.coffee.model.DrinkSizeKey;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "drink_sizes")
public class DrinkSize {

    @EmbeddedId
    private DrinkSizeKey id;

    private Double price;

    @JsonIgnore
    @ManyToOne
    @MapsId("drinkId")
    @JoinColumn(name = "drink_id")
    private Drink drink;

    @ManyToOne
    @MapsId("sizeId")
    @JoinColumn(name = "size_id")
    private Size size;
}
