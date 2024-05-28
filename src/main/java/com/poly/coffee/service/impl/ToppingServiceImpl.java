package com.poly.coffee.service.impl;

import com.poly.coffee.dto.request.ToppingRequest;
import com.poly.coffee.dto.response.ToppingResponse;
import com.poly.coffee.entity.Topping;
import com.poly.coffee.exception.AppException;
import com.poly.coffee.exception.ErrorCode;
import com.poly.coffee.mapper.ToppingMapper;
import com.poly.coffee.repository.ToppingRepository;
import com.poly.coffee.service.ToppingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class ToppingServiceImpl implements ToppingService {

    ToppingRepository toppingRepository;

    ToppingMapper toppingMapper;

    @Override
    public ToppingResponse createTopping(ToppingRequest request) {
        Topping topping = toppingMapper.toTopping(request);

        return toppingMapper.toToppingResponse(toppingRepository.save(topping));
    }

    @Override
    public List<ToppingResponse> getAllToppings() {
        return toppingRepository.findAll()
                .stream()
                .map(toppingMapper::toToppingResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ToppingResponse getToppingById(Long id) {
        Topping topping = findToppingById(id);
        return toppingMapper.toToppingResponse(topping);
    }

    private Topping findToppingById(Long id) {
        return toppingRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.TOPPING_NOT_FOUND));
    }

    @Override
    public ToppingResponse updateTopping(Long id, ToppingRequest request) {
        Topping topping = findToppingById(id);

        toppingMapper.updateTopping(topping, request);

        return toppingMapper.toToppingResponse(toppingRepository.save(topping));
    }

    @Override
    public void deleteTopping(Long id) {
        Topping topping = findToppingById(id);

        toppingRepository.deleteById(id);
    }
}
