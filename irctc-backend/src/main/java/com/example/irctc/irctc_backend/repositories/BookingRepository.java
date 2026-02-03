package com.example.irctc.irctc_backend.repositories;

import com.example.irctc.irctc_backend.Entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
