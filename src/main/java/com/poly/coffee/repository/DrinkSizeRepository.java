package com.poly.coffee.repository;

import com.poly.coffee.entity.DrinkSize;
import com.poly.coffee.model.DrinkSizeKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DrinkSizeRepository extends JpaRepository<DrinkSize, DrinkSizeKey> {

    List<DrinkSize> findByDrinkId(Long drinkId);
}
