package com.it.telescopeplatform.telescope.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.it.telescopeplatform.image.dtos.ImageResponseDto;
import com.it.telescopeplatform.review.dtos.ReviewResponseDto;
import com.it.telescopeplatform.review.models.Review;
import com.it.telescopeplatform.telescope.dtos.TelescopeResponseDto;
import com.it.telescopeplatform.telescope.models.Telescope;

public class TelescopeMapper {
        public static TelescopeResponseDto toDto(Telescope telescope) {
                if (telescope == null)
                        return null;

                List<ImageResponseDto> imageDtos = telescope.getImages() == null
                                ? List.of()
                                : telescope.getImages().stream()
                                .map(img -> ImageResponseDto.builder()
                                        .id(img.getId())
                                        .url(img.getImageUrl())
                                        .build())
                                        .collect(Collectors.toList());

                List<ReviewResponseDto> reviewDtos = telescope.getReviews() == null
                                ? List.of()
                                : telescope.getReviews().stream()
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

                var avgRating = telescope.getReviews() != null && !telescope.getReviews().isEmpty()
                                ? telescope.getReviews().stream()
                                                .mapToDouble(Review::getRating)
                                                .average()
                                                .orElse(0.0)
                                : null;

                return TelescopeResponseDto.builder()
                                .id(telescope.getId())
                                .name(telescope.getName())
                                .description(telescope.getDescription())
                                .brand(telescope.getBrand())
                                .type(telescope.getType())
                                .aperture(telescope.getAperture())
                                .pricePerDay(telescope.getPricePerDay())
                                .available(telescope.getAvailable())
                                .images(imageDtos)
                                .publisherId(telescope.getPublisher() != null ? telescope.getPublisher().getId()
                                                : null)
                                .averageRating(avgRating)
                                .createdAt(telescope.getCreatedAt())
                                .reviews(reviewDtos)
                                .build();
        }

        public static List<TelescopeResponseDto> toDto(List<Telescope> telescopes) {
                if (telescopes == null || telescopes.isEmpty())
                        return List.of();

                return telescopes.stream()
                                .map(TelescopeMapper::toDto)
                                .collect(Collectors.toList());
        }
}
