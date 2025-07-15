package com.it.telescopeplatform.image.dtos;

import lombok.Builder;
import lombok.Data;

@Data @Builder
public class ImageResponseDto {
    private Long id;
    private String url;
}
