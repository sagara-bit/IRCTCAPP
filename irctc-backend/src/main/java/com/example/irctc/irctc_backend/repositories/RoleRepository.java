package com.example.irctc.irctc_backend.repositories;

import com.example.irctc.irctc_backend.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,String> {

    Optional<Role> findByName(String name);
    boolean existsByName(String name);
}
