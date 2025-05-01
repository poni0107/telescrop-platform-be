package com.it.telescopeplatform.user.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserFavoriteId implements Serializable {
    private Long userId;
    private Long telescopeId;
}