package com.poly.coffee.service;

import com.poly.coffee.dto.request.SizeRequest;
import com.poly.coffee.dto.response.SizeResponse;

import java.util.List;

public interface SizeService {

    SizeResponse createSize(SizeRequest request);

    List<SizeResponse> getAllSizes();

    SizeResponse getSizeById(Long id);

    SizeResponse updateSize(Long id, SizeRequest request);

    void deleteSize(Long id);
}
