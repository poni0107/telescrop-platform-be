package com.it.telescopeplatform.image.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it.telescopeplatform.image.models.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {
}