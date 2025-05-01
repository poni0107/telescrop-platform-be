package com.it.telescopeplatform.accessory.api;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.it.telescopeplatform.accessory.dtos.AccessoryImageDto;
import com.it.telescopeplatform.accessory.dtos.AccessoryRequestDto;
import com.it.telescopeplatform.accessory.dtos.AccessoryResponseDto;
import com.it.telescopeplatform.accessory.dtos.AccessoryReviewDto;
import com.it.telescopeplatform.accessory.services.AccessoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/accessories")
@RequiredArgsConstructor
public class AccessoryController {
    private final AccessoryService accessoryService;

    @GetMapping
    public List<AccessoryResponseDto> getAll() {
        return accessoryService.getAccessories();
    }

    @GetMapping("/{id}")
    public AccessoryResponseDto getById(@PathVariable Long id) {
        return accessoryService.getAccessoryById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public AccessoryResponseDto create(@RequestBody AccessoryRequestDto dto) {
        return accessoryService.createAccessory(dto);
    }

    @PostMapping("/{id}/image")
    @PreAuthorize("hasRole('Admin')")
    public AccessoryResponseDto addImages(@PathVariable Long id, @RequestBody AccessoryImageDto accessoryImageDto) {
        return accessoryService.addImages(id, accessoryImageDto);
    }

    @PostMapping("/{id}/review")
    @PreAuthorize("hasRole('Customer')")
    public AccessoryResponseDto addReview(@PathVariable Long id, @RequestBody AccessoryReviewDto review) {
        return accessoryService.addReview(id, review);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public AccessoryResponseDto update(@PathVariable Long id, @RequestBody AccessoryRequestDto accessory) {
        return accessoryService.updateAccessory(id, accessory);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public void delete(@PathVariable Long id) {
        accessoryService.deleteAccessory(id);
    }

    @DeleteMapping("/{id}/image/{imageId}")
    @PreAuthorize("hasRole('Admin')")
    public void deleteImage(@PathVariable Long id, @PathVariable Long imageId) {
        accessoryService.deleteImage(id, imageId);
    }

    @DeleteMapping("/{id}/review/{reviewId}")
    public void deleteReview(@PathVariable Long id, @PathVariable Long reviewId) {
        accessoryService.deleteReview(id, reviewId);
    }
}