package com.poly.coffee.mapper;

import com.poly.coffee.dto.request.SizeRequest;
import com.poly.coffee.dto.response.SizeResponse;
import com.poly.coffee.entity.Size;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SizeMapper {

    Size toSize(SizeRequest request);

    void updateSize(@MappingTarget Size size, SizeRequest request);

    SizeResponse toSizeResponse(Size size);
}
