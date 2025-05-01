package com.it.telescopeplatform.user.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.it.telescopeplatform.configs.errorhandlers.ResourceNotFoundException;
import com.it.telescopeplatform.telescope.repositories.TelescopeRepository;
import com.it.telescopeplatform.user.models.UserFavorite;
import com.it.telescopeplatform.user.repositories.UserFavoriteRepository;
import com.it.telescopeplatform.user.services.UserFavoriteService;
import com.it.telescopeplatform.user.services.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFavoriteServiceImpl implements UserFavoriteService {

    private final UserFavoriteRepository userFavoriteRepository;
    private final TelescopeRepository telescopeRepository;
    private final UserService userService;

    @Override
    public List<UserFavorite> getUserFavorites() {
        var user = userService.getCurrentUser();
        return userFavoriteRepository.findByUserId(user);
    }

    @Override
    public UserFavorite addToFavorites(Long telescopeId) {
        var user = userService.getCurrentUser();
        var telescope = telescopeRepository.findById(telescopeId)
                .orElseThrow(() -> new ResourceNotFoundException("Telescope not found"));
        var favorite = userFavoriteRepository.findByUserIdAndTelescopeId(user, telescope);
        if (favorite != null) {
            throw new ResourceNotFoundException("Telescope already in favorites");
        }
        var newFavorite = new UserFavorite();
        newFavorite.setUserId(user);
        newFavorite.setTelescopeId(telescope);
        return userFavoriteRepository.save(newFavorite);
    }

    @Override
    public void removeFromFavorites(Long telescopeId) {
        var user = userService.getCurrentUser();
        var telescope = telescopeRepository.findById(telescopeId)
                .orElseThrow(() -> new ResourceNotFoundException("Telescope not found"));
        var favorite = userFavoriteRepository.findByUserIdAndTelescopeId(user, telescope);
        if (favorite == null) {
            throw new ResourceNotFoundException("Telescope not found in favorites");
        }
        userFavoriteRepository.delete(favorite);
    }

}
