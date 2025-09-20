package com.example.irctc.irctc_backend.service.impl;

import com.example.irctc.irctc_backend.Dto.TrainSeatDto;
import com.example.irctc.irctc_backend.Entity.TrainSchedule;
import com.example.irctc.irctc_backend.Entity.TrainSeat;
import com.example.irctc.irctc_backend.Exception.ResourceNotFoundException;
import com.example.irctc.irctc_backend.repositories.TrainScheduleRepository;
import com.example.irctc.irctc_backend.repositories.TrainSeatRepository;
import com.example.irctc.irctc_backend.service.TrainSeatService;
import jakarta.transaction.Transactional;
import lombok.Synchronized;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainSeatServiceImpl implements TrainSeatService {

    private TrainSeatRepository trainSeatRepository;
    private TrainScheduleRepository trainScheduleRepository;
    private ModelMapper modelMapper;

    public TrainSeatServiceImpl(TrainSeatRepository trainSeatRepository, TrainScheduleRepository trainScheduleRepository, ModelMapper modelMapper) {
        this.trainSeatRepository = trainSeatRepository;
        this.trainScheduleRepository = trainScheduleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TrainSeatDto createSeatInformation(TrainSeatDto trainSeatDto) {
         TrainSchedule trainSchedule = trainScheduleRepository.findById(trainSeatDto.getTrainScheduleId()).orElseThrow(()-> new ResourceNotFoundException("Train Scheduled not found"));
        TrainSeat trainSeat = modelMapper.map(trainSeatDto,TrainSeat.class);
        trainSeat.setTrainSchedule(trainSchedule);
        TrainSeat savedTrainSeat = trainSeatRepository.save(trainSeat);
        return modelMapper.map(savedTrainSeat,TrainSeatDto.class);

    }

    @Override
    public List<TrainSeatDto> getSeatInfoByTrainScheduleId(Long scheduleId) {
      List<TrainSeat> trainSeats = trainSeatRepository.findByTrainScheduleId(scheduleId);
      return trainSeats.stream().map(trainSeat -> modelMapper.map(trainSeat,TrainSeatDto.class)).toList();

    }

    @Override
    public void deleteSeatInfo(Long seatId) {
        TrainSeat trainSeat =  trainSeatRepository.findById(seatId).orElseThrow(()-> new ResourceNotFoundException("Train Seat Not Found"));
        trainSeatRepository.delete(trainSeat);
    }

    @Override
    public TrainSeatDto updateSeatInfo(Long seatId, TrainSeatDto trainSeatDto) {
       TrainSeat trainSeat = trainSeatRepository.findById(seatId).orElseThrow(()-> new ResourceNotFoundException("Train Seat Not found"));
        TrainSchedule trainSchedule = trainScheduleRepository.findById(trainSeatDto.getTrainScheduleId()).orElseThrow(()-> new ResourceNotFoundException("Train Scheduled not found"));
        trainSeat.setTrainSchedule(trainSchedule);
        trainSeat.setCoachType(trainSeatDto.getCoachType());
        trainSeat.setAvailableSeats(trainSeatDto.getAvailableSeats());
        trainSeat.setPrice(trainSeatDto.getPrice());
        trainSeat.setTotalSeats(trainSeatDto.getTotalSeats());
        trainSeat.setTrainSeatOrder(trainSeatDto.getTrainSeatOrder());
        trainSeat.setSeatNumberToAssign(trainSeatDto.getSeatNumberToAssign());
        TrainSeat updatedTrainSeat = trainSeatRepository.save(trainSeat);
        return  modelMapper.map(updatedTrainSeat,TrainSeatDto.class);
    }


    @Synchronized
    @Transactional
    // not able to access multiple thread
    public List<Integer> bookSeat(int seatToBook,Long seatId){
        TrainSeat trainSeat = trainSeatRepository.findById( seatId).orElseThrow(()-> new ResourceNotFoundException("Train Seat Not Found"));

        if(trainSeat.isSeatAvailable(seatToBook)){
            trainSeat.setAvailableSeats(trainSeat.getAvailableSeats()-seatToBook);
            List<Integer> bookedSeats = new ArrayList<>();
            for(int i=1;i<=seatToBook;i++){

                bookedSeats.add(trainSeat.getSeatNumberToAssign());
                trainSeat.setSeatNumberToAssign(trainSeat.getSeatNumberToAssign()+1);
            }
            trainSeatRepository.save(trainSeat);
            return  bookedSeats;
        }else {
            throw new IllegalStateException("No seats abailable in this coach");
        }
    }
}
