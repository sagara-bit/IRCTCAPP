package com.example.irctc.irctc_backend.service.impl;

import com.example.irctc.irctc_backend.Dto.BookingPassengerDto;
import com.example.irctc.irctc_backend.Dto.BookingRequest;
import com.example.irctc.irctc_backend.Dto.BookingResponse;
import com.example.irctc.irctc_backend.Dto.StationDto;
import com.example.irctc.irctc_backend.Entity.*;
import com.example.irctc.irctc_backend.Exception.ResourceNotFoundException;
import com.example.irctc.irctc_backend.repositories.*;
import com.example.irctc.irctc_backend.service.BookingService;

import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@Service
public class BookingServiceImpl implements BookingService {
    private static final Logger log = LoggerFactory.getLogger(BookingServiceImpl.class);
    private BookingPassengerRepository bookingPassengerRepository;
    private BookingRepository bookingRepository;
    private UserRepository userRepository;
    private TrainScheduleRepository  trainScheduleRepository;
    private TrainRepository trainRepository;
    private StationRepository stationRepository;
    private TrainSeatRepository trainSeatRepository;
    private ModelMapper modelMapper;

    public BookingServiceImpl(BookingPassengerRepository bookingPassengerRepository, BookingRepository bookingRepository, UserRepository userRepository, TrainScheduleRepository trainScheduleRepository, TrainRepository trainRepository, StationRepository stationRepository, TrainSeatRepository trainSeatRepository, ModelMapper modelMapper) {
        this.bookingPassengerRepository = bookingPassengerRepository;
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.trainScheduleRepository = trainScheduleRepository;
        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;
        this.trainSeatRepository = trainSeatRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public synchronized BookingResponse createBooking(BookingRequest bookingRequest) {
       User user = userRepository.findById(bookingRequest.getUserId()).orElseThrow( () -> new ResourceNotFoundException("User not found"));

        TrainSchedule trainSchedule = this.trainScheduleRepository.findById(bookingRequest.getTrainScheduleId()).orElseThrow(() -> new ResourceNotFoundException("Source Station"));
        Station sourceStation = this.stationRepository.findById(bookingRequest.getSourceStationId()).orElseThrow(() -> new ResourceNotFoundException("Source Station"));
        Station destinationStation = this.stationRepository.findById(bookingRequest.getDestinationStationId()).orElseThrow(() -> new ResourceNotFoundException("Destination Station"));

        //method: we can write and reuse this method
        List<TrainSeat> coaches = trainSchedule.getTrainSeats();
        coaches.sort((s1, s2) -> s1.getTrainSeatOrder() - s2.getTrainSeatOrder());

        int totalReqestedSeat = bookingRequest.getPassengers().size();
        log.info("TrainScheduleId={}", trainSchedule.getId());
        log.info("Total seats found={}", coaches.size());
        log.info("Requested coachType={}", bookingRequest.getCoachType());

        coaches.forEach(seat ->
                log.info("SeatId={} type={} available={}",
                        seat.getId(),
                        seat.getCoachType(),
                        seat.getAvailableSeats())
        );
        List<TrainSeat> selectedCoaches = coaches.stream().filter(coach -> coach.getCoachType()==bookingRequest.getCoachType()).toList();

        TrainSeat coachToBookSeat = null;
        //which coach seat is going to book
        for(TrainSeat coach : selectedCoaches){
            System.out.println(coach.getId() +"id from coach");
            if(coach.isSeatAvailable(totalReqestedSeat)) {
                coachToBookSeat = coach;
                break;
            }
        }


        //coachToBookSeat
        if(coachToBookSeat==null){
            throw new ResourceNotFoundException("Train Seat Not Available");
        }

        // process to book the seat
        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setSourceStation(sourceStation);
        booking.setDestinationStation(destinationStation);
        booking.setTrainSchedule(trainSchedule);
        booking.setUser(user);
        booking.setCreated(LocalTime.now());
        booking.setJourneyDate(trainSchedule.getRunDate());
        booking.setJourneyDate(trainSchedule.getRunDate());
        booking.setPnr(UUID.randomUUID().toString());

        //set total fare

        booking.setTotalFare(new BigDecimal(totalReqestedSeat*coachToBookSeat.getPrice()));

        Payment payment = new Payment();
        payment.setAmount(booking.getTotalFare());
       payment.setPaymentStatus(PaymentStatus.NOT_PAID);
        payment.setBooking(booking);
        //set the payment
        booking.setPayment(payment);

        List<BookingPassenger> bookingPassengers = new ArrayList<>();

        for(BookingPassengerDto bookingPassengerDto: bookingRequest.getPassengers()){
            BookingPassenger passengers = modelMapper.map(bookingPassengerDto, BookingPassenger.class);
            passengers.setBooking(booking);
            passengers.setTrainSeat(coachToBookSeat);
            passengers.setSeatNumber(coachToBookSeat.getSeatNumberToAssign()+"");
            coachToBookSeat.setSeatNumberToAssign(coachToBookSeat.getSeatNumberToAssign()+1);
            coachToBookSeat.setAvailableSeats(coachToBookSeat.getAvailableSeats()-1);
            bookingPassengers.add(passengers);
        }

        booking.setPassengers(bookingPassengers);
        Booking savedBooking = bookingRepository.save(booking);
        this.trainSeatRepository.save(coachToBookSeat);

        BookingResponse bookingResponse = new BookingResponse();
        bookingResponse.setBookingId(booking.getId());
        bookingResponse.setPnr(booking.getPnr());
        bookingResponse.setJourneyDate(booking.getJourneyDate());
        bookingResponse.setTotalFare(booking.getTotalFare());
        bookingResponse.setBookingStatus(booking.getBookingStatus());
        bookingResponse.setSourceStation(modelMapper.map(sourceStation, StationDto.class));
        bookingResponse.setDestinationStation(modelMapper.map(destinationStation, StationDto.class));
        bookingResponse.setPaymentStatus(savedBooking.getPayment().getPaymentStatus());

        bookingResponse.setPassengers(
                savedBooking.getPassengers().stream().map(passenger->{
                    BookingPassengerDto bookingPassengerDto = modelMapper.map(passenger, BookingPassengerDto.class);
                    bookingPassengerDto.setCoachId(passenger.getTrainSeat().getId()+ "");
                    return bookingPassengerDto;
                }).toList()
        );

        TrainRoute sourceRoute = trainSchedule.getTrain().getRoutes().stream().filter(route-> route.getStation().getId().equals(sourceStation.getId())).findFirst().get();

        bookingResponse.setDepartureTime(sourceRoute.getDepartureTime());
        bookingResponse.setArrivalTime(sourceRoute.getArrivalTime());

        return bookingResponse;
    }
}
