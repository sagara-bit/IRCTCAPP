package com.example.irctc.irctc_backend.controllers.user;

import com.example.irctc.irctc_backend.Dto.BookingRequest;
import com.example.irctc.irctc_backend.Dto.BookingResponse;
import com.example.irctc.irctc_backend.service.BookingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/bookings")
public class BookingController {
    private BookingService bookingService;
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }
    @PostMapping
    public ResponseEntity<BookingResponse> createBooking(@RequestBody BookingRequest bookingRequest){
        BookingResponse bookingResponse = this.bookingService.createBooking(bookingRequest);
        return new ResponseEntity<>(bookingResponse, HttpStatus.CREATED);
    }

    //get booking detail -->pnr

    //After booking you can download a pdf
}
