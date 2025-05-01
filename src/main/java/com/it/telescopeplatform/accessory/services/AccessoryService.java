package com.it.telescopeplatform.accessory.services;

import java.util.List;

import com.it.telescopeplatform.accessory.dtos.AccessoryImageDto;
import com.it.telescopeplatform.accessory.dtos.AccessoryRequestDto;
import com.it.telescopeplatform.accessory.dtos.AccessoryResponseDto;
import com.it.telescopeplatform.accessory.dtos.AccessoryReviewDto;

public interface AccessoryService {
    List<AccessoryResponseDto> getAccessories();

    AccessoryResponseDto getAccessoryById(Long id);

    AccessoryResponseDto createAccessory(AccessoryRequestDto accessoryDto);

    AccessoryResponseDto updateAccessory(Long id, AccessoryRequestDto accessoryDto);

    AccessoryResponseDto addImages(Long id, AccessoryImageDto accessoryImageDto);

    AccessoryResponseDto addReview(Long id, AccessoryReviewDto accessoryReviewDto);

    void deleteAccessory(Long id);

    void deleteImage(Long id, Long imageId);

    void deleteReview(Long id, Long reviewId);
}
