package com.example.irctc.irctc_backend.Dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private  String name;
    private  String email;
    private  String password;
    private String phone;
    private LocalDateTime created;
    private List<RoleDto> roles = new ArrayList<>();
}
