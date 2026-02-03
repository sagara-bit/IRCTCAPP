package com.example.irctc.irctc_backend.repositories;

import com.example.irctc.irctc_backend.Entity.BookingPassenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingPassengerRepository extends JpaRepository<BookingPassenger, Long> {
}
