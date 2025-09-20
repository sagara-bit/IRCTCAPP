package com.example.irctc.irctc_backend.service.impl;

import com.example.irctc.irctc_backend.Dto.TrainDTO;
import com.example.irctc.irctc_backend.Entity.Station;
import com.example.irctc.irctc_backend.Entity.Train;
import com.example.irctc.irctc_backend.Exception.ResourceNotFoundException;
import com.example.irctc.irctc_backend.repositories.StationRepository;
import com.example.irctc.irctc_backend.repositories.TrainRepository;
import com.example.irctc.irctc_backend.service.TrainService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TrainServiceImpl implements TrainService {
    private StationRepository stationRepository;
    private ModelMapper modelMapper;
    private TrainRepository trainRepository;
    @Override
    public TrainDTO createTrain(TrainDTO trainDTO) {
       Long sourceStationId = trainDTO.getSourceStation().getId();
       Long destinationStationId = trainDTO.getDestinationStation().getId();
        Station sourceStation = stationRepository.findById(sourceStationId).orElseThrow(() -> new ResourceNotFoundException("Station Not Found"));
        Station destinationStation = stationRepository.findById(destinationStationId).orElseThrow(()-> new ResourceNotFoundException("destination Station Not Found"));
        Train train = modelMapper.map(trainDTO,Train.class);
        train.setTrainNo(trainDTO.getTrainNo());
        train.setSourceStation(sourceStation);
        train.setDestinationStation(destinationStation);
        Train savedTrain = trainRepository.save(train);
        return  modelMapper.map(savedTrain,TrainDTO.class);
    }

    @Override
    public List<TrainDTO> getAllTrains() {
       List<Train> allTrain = trainRepository.findAll();
       return allTrain.stream().map(train -> modelMapper.map(train,TrainDTO.class)).toList();
    }

    @Override
    public TrainDTO getTrainById(Long id) {
        Train train = trainRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Train Not Found"));
        return  modelMapper.map(train,TrainDTO.class);
    }

    @Override
    public TrainDTO updateTrain(Long id, TrainDTO trainDTO) {
        Train train = trainRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Train Not Found" + trainDTO.getTrainNo()));
        train.setName(trainDTO.getName());
        train.setTrainNo(trainDTO.getTrainNo());
        train.setTotalDistance(trainDTO.getTotalDistance());

        //fetch source and destination stations

        Station sourceStation = stationRepository.findById(trainDTO.getSourceStation().getId()).orElseThrow(()-> new ResourceNotFoundException("source station Not found" + trainDTO.getSourceStation().getId()));
        Station destinationStation = stationRepository.findById((trainDTO.getDestinationStation().getId())).orElseThrow(()-> new ResourceNotFoundException("Destination not fount"+ trainDTO.getDestinationStation().getId()));
        train.setDestinationStation(sourceStation);
        train.setDestinationStation(destinationStation);
        return  modelMapper.map(trainRepository.save(train),TrainDTO.class);
    }

    @Override
    public void deleteTrain(Long id) {
        Train train = trainRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Train Not Found" + id));
        trainRepository.delete(train);
    }
}
