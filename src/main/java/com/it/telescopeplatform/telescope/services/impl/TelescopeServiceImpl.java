package com.it.telescopeplatform.telescope.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.it.telescopeplatform.configs.errorhandlers.ResourceNotFoundException;
import com.it.telescopeplatform.image.models.Image;
import com.it.telescopeplatform.image.repositories.ImageRepository;
import com.it.telescopeplatform.publisher.repositories.PublisherRepository;
import com.it.telescopeplatform.reservation.enums.ReservationStatus;
import com.it.telescopeplatform.reservation.repositories.ReservationRepository;
import com.it.telescopeplatform.review.models.Review;
import com.it.telescopeplatform.review.repositories.ReviewRepository;
import com.it.telescopeplatform.telescope.dtos.TelescopeImageDto;
import com.it.telescopeplatform.telescope.dtos.TelescopeRequestDto;
import com.it.telescopeplatform.telescope.dtos.TelescopeReviewDto;
import com.it.telescopeplatform.telescope.models.Telescope;
import com.it.telescopeplatform.telescope.repositories.TelescopeRepository;
import com.it.telescopeplatform.telescope.services.TelescopeService;
import com.it.telescopeplatform.user.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TelescopeServiceImpl implements TelescopeService {

    private final TelescopeRepository telescopeRepository;
    private final PublisherRepository publisherRepository;
    private final ImageRepository imageRepository;
    private final ReservationRepository reservationRepository;
    private final ReviewRepository reviewRepository;
    private final UserService userService;

    @Override
    public List<Telescope> getTelescopes() {
        return telescopeRepository.findByAvailableTrue();
    }

    @Override
    public Telescope getTelescopeById(Long id) {
        return telescopeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Telescope not found"));
    }

    @Override
    public Telescope createTelescope(TelescopeRequestDto telescopeDto) {
        var telescope = new Telescope();
        telescope.setName(telescopeDto.getName());
        telescope.setDescription(telescopeDto.getDescription());
        telescope.setBrand(telescopeDto.getBrand());
        telescope.setType(telescopeDto.getType());
        telescope.setAperture(telescopeDto.getAperture());
        telescope.setPricePerDay(telescopeDto.getPricePerDay());
        telescope.setAvailable(telescopeDto.getAvailable());
        telescope.setPublisher(publisherRepository.findById(telescopeDto.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found")));
        return telescopeRepository.save(telescope);
    }

    @Override
    public Telescope updateTelescope(Long id, TelescopeRequestDto telescopeDto) {
        var existingTelescope = telescopeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Telescope not found"));
        existingTelescope.setName(telescopeDto.getName());
        existingTelescope.setDescription(telescopeDto.getDescription());
        existingTelescope.setBrand(telescopeDto.getBrand());
        existingTelescope.setType(telescopeDto.getType());
        existingTelescope.setAperture(telescopeDto.getAperture());
        existingTelescope.setPricePerDay(telescopeDto.getPricePerDay());
        existingTelescope.setAvailable(telescopeDto.getAvailable());
        existingTelescope.setPublisher(publisherRepository.findById(telescopeDto.getPublisherId())
                .orElseThrow(() -> new ResourceNotFoundException("Publisher not found")));
        return telescopeRepository.save(existingTelescope);
    }

    @Override
    public void deleteTelescope(Long id) {
        telescopeRepository.deleteById(id);
    }

    @Override
    public Telescope addImages(Long id, TelescopeImageDto telescopeImageDto) {
        var telescope = telescopeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Telescope not found"));
        List<Image> images = new ArrayList<>();
        for (var imageUrl : telescopeImageDto.getImageUrls()) {
            var telescopeImage = new Image();
            telescopeImage.setTelescope(telescope);
            telescopeImage.setImageUrl(imageUrl);
            images.add(telescopeImage);
        }
        var existingImages = telescope.getImages();
        if (existingImages != null) {
            existingImages.addAll(images);
        } else {
            existingImages = new ArrayList<>();
            existingImages.addAll(images);
        }
        telescope.setImages(existingImages);
        imageRepository.saveAll(images);
        return telescopeRepository.save(telescope);
    }

    @Override
    public void deleteImage(Long id, Long imageId) {
        var telescope = telescopeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Telescope not found"));
        var image = imageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Image not found"));
        telescope.getImages().remove(image);
        imageRepository.delete(image);
        telescopeRepository.save(telescope);
    }

    @Override
    public Telescope addReview(Long id, TelescopeReviewDto telescopeReviewDto) {
        var telescope = telescopeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Telescope not found"));
        var user = userService.getCurrentUser();
        var reservations = reservationRepository.findByUserIdAndTelescopeId(user.getId(), id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "You must make a reservation for this telescope before reviewing it"));
        if (reservations.stream().allMatch(reservation -> reservation.getStatus().equals(ReservationStatus.Cancelled)
                || reservation.getStatus().equals(ReservationStatus.Pending))) {
            throw new ResourceNotFoundException(
                    "You cannot review a telescope with a cancelled or pending reservation");
        }
        var hasReview = telescope.getReviews().stream()
                .anyMatch(r -> r.getUser().getId().equals(user.getId()));
        if (hasReview) {
            throw new ResourceNotFoundException("You have already reviewed this telescope");
        }
        var telescopeReview = new Review();
        telescopeReview.setTelescope(telescope);
        telescopeReview.setUser(user);
        telescopeReview.setRating(telescopeReviewDto.getRating());
        telescopeReview.setReviewText(telescopeReviewDto.getReviewText());
        var review = reviewRepository.save(telescopeReview);
        telescope.getReviews().add(review);
        return telescopeRepository.save(telescope);
    }

    @Override
    public void deleteReview(Long id, Long reviewId) {
        var telescope = telescopeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Telescope not found"));
        var user = userService.getCurrentUser();
        var review = telescope.getReviews().stream()
                .filter(r -> r.getId().equals(reviewId) && r.getUser().getId().equals(user.getId()))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Review not found"));
        telescope.getReviews().remove(review);
        reviewRepository.delete(review);
        telescopeRepository.save(telescope);
    }
}
