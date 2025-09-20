package com.example.irctc.irctc_backend.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name;
    @Column(unique = true, nullable = false)
    private  String email;
    private  String password;
    private String phone;
    private LocalDateTime created;
    private UserRole userRole = UserRole.ROLE_NORMAL;
    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();
}
