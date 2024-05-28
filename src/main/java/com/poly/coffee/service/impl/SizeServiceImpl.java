package com.poly.coffee.service.impl;

import com.poly.coffee.dto.request.SizeRequest;
import com.poly.coffee.dto.response.SizeResponse;
import com.poly.coffee.entity.Size;
import com.poly.coffee.exception.AppException;
import com.poly.coffee.exception.ErrorCode;
import com.poly.coffee.mapper.SizeMapper;
import com.poly.coffee.repository.SizeRepository;
import com.poly.coffee.service.SizeService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class SizeServiceImpl implements SizeService {

    SizeRepository sizeRepository;

    SizeMapper sizeMapper;

    @Override
    public SizeResponse createSize(SizeRequest request) {
        Size size = sizeMapper.toSize(request);

        return sizeMapper.toSizeResponse(sizeRepository.save(size));
    }

    @Override
    public List<SizeResponse> getAllSizes() {
        return sizeRepository.findAll()
                .stream()
                .map(sizeMapper::toSizeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SizeResponse getSizeById(Long id) {
        Size size = findSizeById(id);
        return sizeMapper.toSizeResponse(size);
    }

    private Size findSizeById(Long id) {
        return sizeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.SIZE_NOT_FOUND));
    }

    @Override
    public SizeResponse updateSize(Long id, SizeRequest request) {
        Size size = findSizeById(id);

        sizeMapper.updateSize(size, request);

        return sizeMapper.toSizeResponse(sizeRepository.save(size));
    }

    @Override
    public void deleteSize(Long id) {
        Size size = findSizeById(id);
        sizeRepository.deleteById(id);
    }
}
