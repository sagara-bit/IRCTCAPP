package com.example.irctc.irctc_backend.service;

import com.example.irctc.irctc_backend.Dto.BookingRequest;
import com.example.irctc.irctc_backend.Dto.BookingResponse;

public interface BookingService {

    BookingResponse createBooking(BookingRequest bookingRequest);
}
