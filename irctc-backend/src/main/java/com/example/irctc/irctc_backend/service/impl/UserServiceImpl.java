package com.example.irctc.irctc_backend.service.impl;

import com.example.irctc.irctc_backend.Dto.UserDto;
import com.example.irctc.irctc_backend.Entity.Role;
import com.example.irctc.irctc_backend.Entity.User;
import com.example.irctc.irctc_backend.Exception.ResourceNotFoundException;
import com.example.irctc.irctc_backend.repositories.RoleRepository;
import com.example.irctc.irctc_backend.repositories.UserRepository;
import com.example.irctc.irctc_backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto registerUser(UserDto userDto) {
        User user = modelMapper.map(userDto,User.class);
        System.out.println(user+"user1121");
     Role role= roleRepository.findByName("ROLE_NORMAL").orElseThrow(()-> new ResourceNotFoundException("server is not configured properly, please contact support"));
     System.out.println(role +" role is coming from repository");
        user.getRoles().clear();
        user.getRoles().add(role);
        System.out.println(user.getRoles() +" role is coming from repository");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreated(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser,UserDto.class);

    }
}
