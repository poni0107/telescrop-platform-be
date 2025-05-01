package com.it.telescopeplatform.accessory.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.it.telescopeplatform.accessory.dtos.AccessoryResponseDto;
import com.it.telescopeplatform.accessory.models.Accessory;
import com.it.telescopeplatform.image.models.Image;
import com.it.telescopeplatform.review.models.Review;

@Component
public class AccessoryMapper {
        public static AccessoryResponseDto toDto(Accessory accessory) {
                if (accessory == null) {
                        return null;
                }

                List<String> imageUrls = accessory.getImages() != null
                                ? accessory.getImages().stream()
                                                .map(Image::getImageUrl)
                                                .collect(Collectors.toList())
                                : List.of();

                var avgRating = accessory.getReviews() != null && !accessory.getReviews().isEmpty()
                                ? accessory.getReviews().stream()
                                                .mapToDouble(Review::getRating)
                                                .average()
                                                .orElse(0.0)
                                : null;

                return AccessoryResponseDto.builder()
                                .id(accessory.getId())
                                .name(accessory.getName())
                                .description(accessory.getDescription())
                                .category(accessory.getCategory())
                                .price(accessory.getPrice())
                                .stockQuantity(accessory.getStockQuantity())
                                .imageUrls(imageUrls)
                                .publisherName(accessory.getPublisher() != null ? accessory.getPublisher().getName()
                                                : null)
                                .averageRating(avgRating)
                                .createdAt(accessory.getCreatedAt())
                                .build();
        }

        public static List<AccessoryResponseDto> toDto(List<Accessory> accessories) {
                if (accessories == null || accessories.isEmpty())
                        return List.of();

                return accessories.stream()
                                .map(AccessoryMapper::toDto)
                                .collect(Collectors.toList());
        }
}
