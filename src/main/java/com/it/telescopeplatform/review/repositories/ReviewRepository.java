package com.it.telescopeplatform.review.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.it.telescopeplatform.review.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}