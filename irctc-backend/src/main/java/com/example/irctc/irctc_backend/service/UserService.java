package com.example.irctc.irctc_backend.service;

import com.example.irctc.irctc_backend.Dto.UserDto;
import org.springframework.stereotype.Service;


public interface UserService {

    public UserDto registerUser(UserDto userDto);
}
