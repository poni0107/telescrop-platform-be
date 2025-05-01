package com.it.telescopeplatform.telescope.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.it.telescopeplatform.image.models.Image;
import com.it.telescopeplatform.review.models.Review;
import com.it.telescopeplatform.telescope.dtos.TelescopeResponseDto;
import com.it.telescopeplatform.telescope.models.Telescope;

public class TelescopeMapper {
        public static TelescopeResponseDto toDto(Telescope telescope) {
                if (telescope == null)
                        return null;

                List<String> imageUrls = telescope.getImages() != null
                                ? telescope.getImages().stream()
                                                .map(Image::getImageUrl)
                                                .collect(Collectors.toList())
                                : List.of();

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
                                .imageUrls(imageUrls)
                                .publisherName(telescope.getPublisher() != null ? telescope.getPublisher().getName()
                                                : null)
                                .averageRating(avgRating)
                                .createdAt(telescope.getCreatedAt())
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
