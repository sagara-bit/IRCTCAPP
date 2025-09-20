package com.example.irctc.irctc_backend.service.impl;

import com.example.irctc.irctc_backend.Dto.TrainRouteDto;
import com.example.irctc.irctc_backend.Entity.Station;
import com.example.irctc.irctc_backend.Entity.Train;
import com.example.irctc.irctc_backend.Entity.TrainRoute;
import com.example.irctc.irctc_backend.Exception.ResourceNotFoundException;
import com.example.irctc.irctc_backend.repositories.StationRepository;
import com.example.irctc.irctc_backend.repositories.TrainRepository;
import com.example.irctc.irctc_backend.repositories.TrainRouteRepository;
import com.example.irctc.irctc_backend.service.TrainRouteService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainRouteServiceImpl implements TrainRouteService {

    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;
    private final TrainRouteRepository trainRouteRepository;
    private final ModelMapper modelMapper;

    public TrainRouteServiceImpl(TrainRepository trainRepository, StationRepository stationRepository, TrainRouteRepository trainRouteRepository, ModelMapper modelMapper) {
        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;
        this.trainRouteRepository = trainRouteRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TrainRouteDto addRoute(TrainRouteDto trainRouteDto) {

        Train train = this.trainRepository.findById(trainRouteDto.getTrain().getId()).orElseThrow(() -> new ResourceNotFoundException("Train Not found" + trainRouteDto.getTrain().getTrainNo()));
        System.out.println("train from 2    "+train.getId());
        System.out.println("train from     "+trainRouteDto.getId());
        Station station = this.stationRepository.findById(trainRouteDto.getStation().getId()).orElseThrow(() -> new ResourceNotFoundException("Startion not Found" + trainRouteDto.getStation().getId()));
        System.out.println(station + " station  from add route");
        // convert DTo to Entity
        TrainRoute trainRoute = modelMapper.map(trainRouteDto, TrainRoute.class);
        trainRoute.setTrain(train);
        trainRoute.setStation(station);
        //save the TrainRoute entity
        TrainRoute savedTrainRoute = trainRouteRepository.save(trainRoute);
       System.out.println(savedTrainRoute + " stationDto    ");
        //convert saves entity back to dto
        return modelMapper.map(savedTrainRoute, TrainRouteDto.class);

    }

    @Override
    public List<TrainRouteDto> getRoutesByTrain(Long trainId) {
        Train train = this.trainRepository.findById(trainId).orElseThrow(() -> new ResourceNotFoundException("Train Not found" + trainId));
        List<TrainRoute> trainRouteDto = this.trainRouteRepository.findByTrain(train);
        return trainRouteDto.stream().map(trainRoute -> modelMapper.map(trainRoute, TrainRouteDto.class)).toList();
    }

    @Override
    public TrainRouteDto updateRote(Long id, TrainRouteDto trainRouteDto) {
        TrainRoute existingRoute = trainRouteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(" Train Route not fount" + id));
        Station station = this.stationRepository.findById(trainRouteDto.getStation().getId()).orElseThrow(()-> new ResourceNotFoundException("Station not found"));
        Train train = this.trainRepository.findById(trainRouteDto.getTrain().getId()).orElseThrow(()-> new ResourceNotFoundException("Train Not Found" + trainRouteDto.getId()));

        existingRoute.setStation(station);
        existingRoute.setTrain(train);
        existingRoute.setStationOrder(trainRouteDto.getStationOrder());
        existingRoute.setArrivalTime(trainRouteDto.getArrivalTime());
        existingRoute.setDepartureTime(trainRouteDto.getDepartureTime());
        existingRoute.setDistanceFromSource(trainRouteDto.getDistanceFromSource());

        //save the updated Route

        TrainRoute trainRoute = trainRouteRepository.save(existingRoute);

        return modelMapper.map(trainRoute,TrainRouteDto.class);
    }

    @Override
    public void deleteRoute(Long id) {

        TrainRoute existingRoute = trainRouteRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException(" Train Route not fount" + id));

        trainRouteRepository.delete(existingRoute);
    }
}
