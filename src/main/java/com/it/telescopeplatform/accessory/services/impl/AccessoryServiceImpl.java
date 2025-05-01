package com.it.telescopeplatform.accessory.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.it.telescopeplatform.accessory.dtos.AccessoryImageDto;
import com.it.telescopeplatform.accessory.dtos.AccessoryRequestDto;
import com.it.telescopeplatform.accessory.dtos.AccessoryResponseDto;
import com.it.telescopeplatform.accessory.dtos.AccessoryReviewDto;
import com.it.telescopeplatform.accessory.mappers.AccessoryMapper;
import com.it.telescopeplatform.accessory.models.Accessory;
import com.it.telescopeplatform.accessory.repositories.AccessoryRepository;
import com.it.telescopeplatform.accessory.services.AccessoryService;
import com.it.telescopeplatform.configs.errorhandlers.ResourceNotFoundException;
import com.it.telescopeplatform.image.models.Image;
import com.it.telescopeplatform.image.repositories.ImageRepository;
import com.it.telescopeplatform.order.enums.OrderStatus;
import com.it.telescopeplatform.order.repositories.OrderRepository;
import com.it.telescopeplatform.publisher.repositories.PublisherRepository;
import com.it.telescopeplatform.review.models.Review;
import com.it.telescopeplatform.review.repositories.ReviewRepository;
import com.it.telescopeplatform.user.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccessoryServiceImpl implements AccessoryService {

    private final AccessoryRepository accessoryRepository;
    private final PublisherRepository publisherRepository;
    private final ImageRepository imageRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;

    private final UserService userService;
    private final AccessoryMapper accessoryMapper;

    @Override
    public List<AccessoryResponseDto> getAccessories() {
        return AccessoryMapper.toDto(accessoryRepository.findAll());
    }

    @Override
    public AccessoryResponseDto getAccessoryById(Long id) {
        var accessory = accessoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accessory not found"));
        return AccessoryMapper.toDto(accessory);
    }

    @Override
    public AccessoryResponseDto createAccessory(AccessoryRequestDto accessoryDto) {
        var publisher = publisherRepository.findById(accessoryDto.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found"));
        var accessory = new Accessory();
        accessory.setPublisher(publisher);
        accessory.setName(accessoryDto.getName());
        accessory.setDescription(accessoryDto.getDescription());
        accessory.setPrice(accessoryDto.getPrice());
        accessory.setCategory(accessoryDto.getCategory());
        accessory.setStockQuantity(accessoryDto.getStockQuantity());

        return AccessoryMapper.toDto(accessoryRepository.save(accessory));
    }

    @Override
    public AccessoryResponseDto updateAccessory(Long id, AccessoryRequestDto accessoryDto) {
        var existingAccessory = accessoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accessory not found"));
        existingAccessory.setPublisher(publisherRepository.findById(accessoryDto.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found")));
        existingAccessory.setName(accessoryDto.getName());
        existingAccessory.setDescription(accessoryDto.getDescription());
        existingAccessory.setPrice(accessoryDto.getPrice());
        existingAccessory.setCategory(accessoryDto.getCategory());
        return AccessoryMapper.toDto(accessoryRepository.save(existingAccessory));
    }

    @Override
    public void deleteAccessory(Long id) {
        accessoryRepository.deleteById(id);
    }

    @Override
    public AccessoryResponseDto addImages(Long id, AccessoryImageDto accessoryImageDto) {
        var accessory = accessoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accessory not found"));
        List<Image> images = new ArrayList<>();
        for (var imageUrl : accessoryImageDto.getImageUrls()) {
            var accessoryImage = new Image();
            accessoryImage.setAccessory(accessory);
            accessoryImage.setImageUrl(imageUrl);
            images.add(accessoryImage);
        }
        var existingImages = accessory.getImages();
        if (existingImages != null) {
            existingImages.addAll(images);
        } else {
            existingImages = new ArrayList<>();
            existingImages.addAll(images);
        }
        accessory.setImages(existingImages);
        imageRepository.saveAll(images);
        return AccessoryMapper.toDto(accessoryRepository.save(accessory));
    }

    @Override
    public void deleteImage(Long id, Long imageId) {
        var accessory = accessoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accessory not found"));
        var image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
        accessory.getImages().remove(image);
        imageRepository.delete(image);
        accessoryRepository.save(accessory);
    }

    @Override
    public AccessoryResponseDto addReview(Long id, AccessoryReviewDto accessoryReviewDto) {
        var accessory = accessoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accessory not found"));
        var user = userService.getCurrentUser();
        var orders = orderRepository.findByUserIdAndAccessoryId(user.getId(), id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "You must order the accessory before reviewing it"));
        if (orders.stream().allMatch(order -> order.getStatus().equals(OrderStatus.Cancelled) ||
                order.getStatus().equals(OrderStatus.Pending))) {
            throw new ResourceNotFoundException("You cannot review an accessory with a cancelled or pending order");
        }
        var hasReview = accessory.getReviews().stream()
                .anyMatch(r -> r.getUser().getId().equals(user.getId()));
        if (hasReview) {
            throw new ResourceNotFoundException("You have already reviewed this accessory");
        }
        var accessoryReview = new Review();
        accessoryReview.setAccessory(accessory);
        accessoryReview.setUser(user);
        accessoryReview.setRating(accessoryReviewDto.getRating());
        accessoryReview.setReviewText(accessoryReviewDto.getReviewText());
        reviewRepository.save(accessoryReview);
        return AccessoryMapper.toDto(accessoryRepository.save(accessory));
    }

    @Override
    public void deleteReview(Long id, Long reviewId) {
        var accessory = accessoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Accessory not found"));
        var user = userService.getCurrentUser();
        var review = accessory.getReviews().stream()
                .filter(r -> r.getId().equals(reviewId) && r.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        accessory.getReviews().remove(review);
        reviewRepository.delete(review);
        accessoryRepository.save(accessory);
    }
}
