package com.poly.coffee.mapper;

import com.poly.coffee.dto.request.ToppingRequest;
import com.poly.coffee.dto.response.ToppingResponse;
import com.poly.coffee.entity.Topping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ToppingMapper {

    Topping toTopping(ToppingRequest request);

    void updateTopping(@MappingTarget Topping topping, ToppingRequest request);

    ToppingResponse toToppingResponse(Topping topping);

}
