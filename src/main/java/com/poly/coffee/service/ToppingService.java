package com.poly.coffee.service;

import com.poly.coffee.dto.request.ToppingRequest;
import com.poly.coffee.dto.response.ToppingResponse;

import java.util.List;

public interface ToppingService {

    ToppingResponse createTopping(ToppingRequest request);

    List<ToppingResponse> getAllToppings();

    ToppingResponse getToppingById(Long id);

    ToppingResponse updateTopping(Long id, ToppingRequest request);

    void deleteTopping(Long id);
}
