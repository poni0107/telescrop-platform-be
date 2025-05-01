package com.it.telescopeplatform.auth.dtos;

public record LoginResponse(String jwt,
                long expiresIn) {
}
