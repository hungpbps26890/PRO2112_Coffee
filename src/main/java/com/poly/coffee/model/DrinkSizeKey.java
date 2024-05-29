package com.poly.coffee.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class DrinkSizeKey implements Serializable {

    @Column(name = "drink_id")
    private Long drinkId;

    @Column(name = "size_id")
    private long sizeId;

}
