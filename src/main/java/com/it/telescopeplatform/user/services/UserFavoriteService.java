package com.it.telescopeplatform.user.services;

import java.util.List;

import com.it.telescopeplatform.user.models.UserFavorite;

public interface UserFavoriteService {

    UserFavorite addToFavorites(Long telescopeId);

    List<UserFavorite> getUserFavorites();

    void removeFromFavorites(Long telescopeId);
}