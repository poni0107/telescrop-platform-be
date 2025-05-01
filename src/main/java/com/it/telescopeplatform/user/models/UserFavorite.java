package com.it.telescopeplatform.user.models;

import java.time.LocalDateTime;

import com.it.telescopeplatform.telescope.models.Telescope;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "User_Favorites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(UserFavoriteId.class)
public class UserFavorite {

    @Id
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    @Id
    @ManyToOne
    @JoinColumn(name = "telescope_id", nullable = false)
    private Telescope telescopeId;

    private LocalDateTime addedAt = LocalDateTime.now();
}