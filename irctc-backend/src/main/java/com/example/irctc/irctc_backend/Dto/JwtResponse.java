package com.example.irctc.irctc_backend.Dto;

import org.springframework.security.core.userdetails.UserDetails;

public record JwtResponse(String token, String username,UserDto user) {
}
