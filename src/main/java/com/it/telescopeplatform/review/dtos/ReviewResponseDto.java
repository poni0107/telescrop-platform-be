package com.it.telescopeplatform.review.dtos;

import lombok.Data;
import lombok.Builder;

@Data
@Builder
public class ReviewResponseDto {
    private Long id;
    private int rating;
    private String reviewText;
    private Long userId;
    private String userFullName;
    private String username;
    private String userEmail;
}
