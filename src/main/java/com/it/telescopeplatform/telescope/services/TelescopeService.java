package com.it.telescopeplatform.telescope.services;

import java.util.List;

import com.it.telescopeplatform.telescope.dtos.TelescopeImageDto;
import com.it.telescopeplatform.telescope.dtos.TelescopeRequestDto;
import com.it.telescopeplatform.telescope.dtos.TelescopeReviewDto;
import com.it.telescopeplatform.telescope.models.Telescope;

public interface TelescopeService {
    List<Telescope> getTelescopes();

    Telescope getTelescopeById(Long id);

    Telescope createTelescope(TelescopeRequestDto telescopeDto);

    Telescope updateTelescope(Long id, TelescopeRequestDto telescopeDto);

    Telescope addImages(Long id, TelescopeImageDto telescopeImageDto);

    Telescope addReview(Long id, TelescopeReviewDto telescopeReviewDto);

    void deleteTelescope(Long id);

    void deleteImage(Long id, Long imageId);

    void deleteReview(Long id, Long reviewId);
}
