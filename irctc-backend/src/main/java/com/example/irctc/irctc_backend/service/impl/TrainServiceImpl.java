package com.example.irctc.irctc_backend.service.impl;

import com.example.irctc.irctc_backend.Dto.AvailabeTrainResponse;
import com.example.irctc.irctc_backend.Dto.TrainDTO;
import com.example.irctc.irctc_backend.Dto.UserTrainSearchRequest;
import com.example.irctc.irctc_backend.Entity.*;
import com.example.irctc.irctc_backend.Exception.ResourceNotFoundException;
import com.example.irctc.irctc_backend.repositories.StationRepository;
import com.example.irctc.irctc_backend.repositories.TrainRepository;
import com.example.irctc.irctc_backend.repositories.TrainScheduleRepository;
import com.example.irctc.irctc_backend.service.TrainService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
public class TrainServiceImpl implements TrainService {
    private StationRepository stationRepository;
    private ModelMapper modelMapper;
    private TrainRepository trainRepository;
    private TrainScheduleRepository trainScheduleRepository;

    public TrainServiceImpl(StationRepository stationRepository, ModelMapper modelMapper, TrainRepository trainRepository, TrainScheduleRepository trainScheduleRepository) {
        this.stationRepository = stationRepository;
        this.modelMapper = modelMapper;
        this.trainRepository = trainRepository;
        this.trainScheduleRepository = trainScheduleRepository;
    }

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

    //user Train Search this method for user to search for specific date for available seats
    @Override
    public List<AvailabeTrainResponse> searchTrains(UserTrainSearchRequest trainSearchRequest) {
        List<TrainRoute> matchedTrains = this.trainRepository.findTrainBySourceAndDestinationInOrder(trainSearchRequest.getSourceStationId(),trainSearchRequest.getDestinationStationId());

      List<AvailabeTrainResponse> list = matchedTrains.stream().map(trainRoute ->{
            TrainSchedule trainSchedules = trainScheduleRepository.findByTrainIdAndRunDate(trainRoute.getTrain().getId(),trainSearchRequest.getJourneyDate()).orElse(null);
            if(trainSchedules==null){
                return null;
            }
            // train is schedude for thr given date
            Map<CoachType,Integer> seatAvailable = new HashMap<>();
            Map<CoachType,Double> priceByCoach = new HashMap<>();
            for(TrainSeat trainSeat :trainSchedules.getTrainSeats()){
                seatAvailable.merge(trainSeat.getCoachType(),trainSeat.getAvailableSeats(),Integer::sum);
                priceByCoach.putIfAbsent(trainSeat.getCoachType(),trainSeat.getPrice());
            }
            return AvailabeTrainResponse.builder()
                    .traindId(trainRoute.getTrain().getId())
                    .trainName(trainRoute.getTrain().getName())
                    .trainNumber(trainRoute.getTrain().getTrainNo())
                    .departureTime(trainRoute.getDepartureTime())
                    .arrivalTime(trainRoute.getArrivalTime())
                    .seatsAvailable(seatAvailable)
                    .priceByCoach(priceByCoach)
                    .build();
        }).filter(Objects::nonNull).toList();
        return list;
    }
}
