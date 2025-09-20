package com.example.irctc.irctc_backend.controllers;

import com.example.irctc.irctc_backend.Dto.ErrorResponse;
import com.example.irctc.irctc_backend.Dto.JwtResponse;
import com.example.irctc.irctc_backend.Dto.LoginRequest;
import com.example.irctc.irctc_backend.Dto.UserDto;
import com.example.irctc.irctc_backend.Entity.User;
import com.example.irctc.irctc_backend.config.Security.JwtHelper;
import com.example.irctc.irctc_backend.repositories.UserRepository;
import com.example.irctc.irctc_backend.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtHelper jwtHelper;
    private final UserService userService;
    private final UserRepository userRepository;
    private  final ModelMapper modelMapper;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtHelper jwtHelper, UserService userService, UserRepository userRepository, ModelMapper modelMapper) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtHelper = jwtHelper;
        this.userService = userService;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {

        // token generate token code
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());

            this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.username());

            String token = this.jwtHelper.generateToken(userDetails);
            User user = userRepository.findByEmail(loginRequest.username()).get();
            JwtResponse jwtResponse = new JwtResponse(token, userDetails.getUsername(),modelMapper.map(user,UserDto.class));
            return new ResponseEntity<>(jwtResponse, HttpStatus.OK);

        } catch (BadCredentialsException ex) {
            ex.printStackTrace();
            System.out.println("Invalid User");
            ErrorResponse errorResponse = new ErrorResponse("Ther usernname or passwoed you entered is incorrect", "403", false);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

    }

    // register new User

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid  @RequestBody UserDto userDto){
        UserDto userDto1 = userService.registerUser(userDto);
        return  new ResponseEntity<>(userDto1,HttpStatus.CREATED);
    }

}