package com.it.telescopeplatform.user.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it.telescopeplatform.telescope.models.Telescope;
import com.it.telescopeplatform.user.models.User;
import com.it.telescopeplatform.user.models.UserFavorite;

public interface UserFavoriteRepository extends JpaRepository<UserFavorite, Long> {
    UserFavorite findByUserIdAndTelescopeId(User user, Telescope telescope);

    List<UserFavorite> findByUserId(User user);
}