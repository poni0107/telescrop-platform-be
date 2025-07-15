package com.it.telescopeplatform.accessory.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.it.telescopeplatform.accessory.dtos.AccessoryResponseDto;
import com.it.telescopeplatform.accessory.models.Accessory;
import com.it.telescopeplatform.image.dtos.ImageResponseDto;
import com.it.telescopeplatform.review.dtos.ReviewResponseDto;
import com.it.telescopeplatform.review.models.Review;

@Component
public class AccessoryMapper {
        public static AccessoryResponseDto toDto(Accessory accessory) {
                if (accessory == null) {
                        return null;
                }

                List<ImageResponseDto> imageDtos = accessory.getImages() == null
                                ? List.of()
                                : accessory.getImages().stream()
                                .map(img -> ImageResponseDto.builder()
                                        .id(img.getId())
                                        .url(img.getImageUrl())
                                        .build())
                                        .collect(Collectors.toList());

                List<ReviewResponseDto> reviewDtos = accessory.getReviews() == null
                                ? List.of()
                                : accessory.getReviews().stream()
                                        .map(r -> ReviewResponseDto.builder()
                                        .id(r.getId())
                                        .reviewText(r.getReviewText())
                                        .rating(r.getRating())
                                        .userId(r.getUser().getId())
                                        .userFullName(r.getUser().getFullName())
                                        .username(r.getUser().getUsername())
                                        .userEmail(r.getUser().getEmail())
                                        .build())
                                        .collect(Collectors.toList());

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
                                .images(imageDtos)
                                .publisherId(accessory.getPublisher() != null ? accessory.getPublisher().getId()
                                                : null)
                                .averageRating(avgRating)
                                .createdAt(accessory.getCreatedAt())
                                .reviews(reviewDtos)
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
