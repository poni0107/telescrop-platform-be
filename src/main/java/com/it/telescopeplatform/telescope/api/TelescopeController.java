package com.it.telescopeplatform.telescope.api;

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

import com.it.telescopeplatform.telescope.dtos.TelescopeImageDto;
import com.it.telescopeplatform.telescope.dtos.TelescopeRequestDto;
import com.it.telescopeplatform.telescope.dtos.TelescopeResponseDto;
import com.it.telescopeplatform.telescope.dtos.TelescopeReviewDto;
import com.it.telescopeplatform.telescope.mappers.TelescopeMapper;
import com.it.telescopeplatform.telescope.services.TelescopeService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/telescopes")
@RequiredArgsConstructor
public class TelescopeController {
    private final TelescopeService telescopeService;

    @GetMapping
    public List<TelescopeResponseDto> getAll() {
        return TelescopeMapper.toDto(telescopeService.getTelescopes());
    }

    @GetMapping("/{id}")
    public TelescopeResponseDto getById(@PathVariable Long id) {
        return TelescopeMapper.toDto(telescopeService.getTelescopeById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('Admin')")
    public TelescopeResponseDto create(@RequestBody TelescopeRequestDto telescopeDto) {
        return TelescopeMapper.toDto(telescopeService.createTelescope(telescopeDto));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public TelescopeResponseDto update(@PathVariable Long id, @RequestBody TelescopeRequestDto telescopeDto) {
        return TelescopeMapper.toDto(telescopeService.updateTelescope(id, telescopeDto));
    }

    @PostMapping("/{id}/images")
    @PreAuthorize("hasRole('Admin')")
    public TelescopeResponseDto addImages(@PathVariable Long id, @RequestBody TelescopeImageDto telescopeImageDto) {
        return TelescopeMapper.toDto(telescopeService.addImages(id, telescopeImageDto));
    }

    @PostMapping("/{id}/review")
    @PreAuthorize("hasRole('Customer')")
    public TelescopeResponseDto addReview(@PathVariable Long id, @RequestBody TelescopeReviewDto telescopeReviewDto) {
        return TelescopeMapper.toDto(telescopeService.addReview(id, telescopeReviewDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('Admin')")
    public void delete(@PathVariable Long id) {
        telescopeService.deleteTelescope(id);
    }

    @DeleteMapping("/{id}/images/{imageId}")
    @PreAuthorize("hasRole('Admin')")
    public void deleteImage(@PathVariable Long id, @PathVariable Long imageId) {
        telescopeService.deleteImage(id, imageId);
    }

    @DeleteMapping("/{id}/review/{reviewId}")
    @PreAuthorize("hasRole('Customer')")
    public void deleteReview(@PathVariable Long id, @PathVariable Long reviewId) {
        telescopeService.deleteReview(id, reviewId);
    }
}