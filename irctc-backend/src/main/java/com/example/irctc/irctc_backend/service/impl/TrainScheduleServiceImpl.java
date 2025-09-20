package com.example.irctc.irctc_backend.service.impl;

import com.example.irctc.irctc_backend.Dto.TrainScheduleDto;
import com.example.irctc.irctc_backend.Entity.Train;
import com.example.irctc.irctc_backend.Entity.TrainSchedule;
import com.example.irctc.irctc_backend.Entity.TrainSeat;
import com.example.irctc.irctc_backend.Exception.ResourceNotFoundException;
import com.example.irctc.irctc_backend.repositories.TrainRepository;
import com.example.irctc.irctc_backend.repositories.TrainScheduleRepository;
import com.example.irctc.irctc_backend.service.TrainScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TrainScheduleServiceImpl implements TrainScheduleService {

    private TrainScheduleRepository trainScheduleRepository;
    private TrainRepository trainRepository;
    private ModelMapper modelMapper;

    public TrainScheduleServiceImpl(TrainScheduleRepository trainScheduleRepository, TrainRepository trainRepository, ModelMapper modelMapper) {
        this.trainScheduleRepository = trainScheduleRepository;
        this.trainRepository = trainRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TrainScheduleDto createSchedule(TrainScheduleDto trainScheduleDto) {
       Train train = trainRepository.findById(trainScheduleDto.getTrainId()).orElseThrow(()-> new ResourceNotFoundException("Train Not Found"));
        TrainSchedule trainSchedule = modelMapper.map(trainScheduleDto,TrainSchedule.class);
        trainSchedule.setTrain(train);
        TrainSchedule saveTrainSchedule = trainScheduleRepository.save(trainSchedule);
        return modelMapper.map(saveTrainSchedule,TrainScheduleDto.class);
    }

    @Override
    public List<TrainScheduleDto> getTrainSchedulesByTrainId(Long trainId) {
        List<TrainSchedule> trainSchedules = trainScheduleRepository.findByTrainId(trainId);
        return trainSchedules.stream().map(trainSchedule -> modelMapper.map(trainSchedule,TrainScheduleDto.class)).toList();

    }

    @Override
    public void deleteTrainSchedule(long trainScheduleId) {
         TrainSchedule trainSchedule = trainScheduleRepository.findById(trainScheduleId).orElseThrow(() -> new ResourceNotFoundException("Train schedule not found"));
         trainScheduleRepository.delete(trainSchedule);
    }

    @Override
    public TrainScheduleDto updateTrainSchedule(Long trainScheduleId, TrainScheduleDto trainScheduleDto) {
         TrainSchedule trainSchedule =  trainScheduleRepository.findById(trainScheduleId).orElseThrow(()-> new ResourceNotFoundException("Train schedule not found"));
         Train train = trainRepository.findById(trainScheduleDto.getTrainId()).orElseThrow(()-> new ResourceNotFoundException("Train not found"));

         trainSchedule.setTrain(train);
         trainSchedule.setAvailableSeats(trainScheduleDto.getAvailableSeats());
         trainSchedule.setRunDate(trainScheduleDto.getRunDate());
         TrainSchedule updateSchedule = trainScheduleRepository.save(trainSchedule);
         return  modelMapper.map(updateSchedule,TrainScheduleDto.class);
    }


}
