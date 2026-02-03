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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.time.LocalDate;
import java.util.*;

@Service
public class TrainServiceImpl implements TrainService {
    private StationRepository stationRepository;
    private ModelMapper modelMapper;
    private TrainRepository trainRepository;
    private TrainScheduleRepository trainScheduleRepository;
    private static final Logger log = LoggerFactory.getLogger(TrainServiceImpl.class);
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
        System.out.println("1111111111111111111111111");
        log.info("No trains matched for sourceStationId={} and destinationStationId={}",
                trainSearchRequest.getSourceStationId(),
                trainSearchRequest.getDestinationStationId());
        List<Train> matchedTrains = this.trainRepository.findTrainBySourceAndDestination(trainSearchRequest.getSourceStationId(),trainSearchRequest.getDestinationStationId());

        System.out.println("matchedTrains"+matchedTrains);

        List<TrainRoute> routes = trainRepository.findAllRoutes();
        System.out.println("ROUTES SIZE = " + routes.size());

        List<Train> validTrain = new ArrayList<>();
        for (Train train : matchedTrains) {
            Integer sourceStation = null;
            Integer destinationStation = null;
            for(TrainRoute trainRoute:train.getRoutes()){
                if(trainRoute.getStation().getId().equals(trainSearchRequest.getSourceStationId())){
                    sourceStation=trainRoute.getStationOrder();
                }else if(trainRoute.getStation().getId().equals(trainSearchRequest.getDestinationStationId())) {
                    destinationStation = trainRoute.getStationOrder();
                }
            }
            //checking schedule date
            boolean runOnThatDay = train.getSchedules().stream().anyMatch(sch -> sch.getRunDate().equals(trainSearchRequest.getJourneyDate()));
            System.out.println(runOnThatDay+"runOnthatDay");
            if(sourceStation!=null && destinationStation!=null&&runOnThatDay){
                validTrain.add(train);
            }

        }

        //create our response
        List<AvailabeTrainResponse> availabeTrainResponse = new ArrayList<>();
        for (Train train : validTrain) {
         TrainSchedule trainSchedule = train.getSchedules().stream().filter(sch->sch.getRunDate().equals(trainSearchRequest.getJourneyDate())).findFirst().orElse(null);
            if(trainSchedule==null){
                continue;
            }
        TrainRoute trainRoute = train.getRoutes().stream().filter(r->r.getStation().getId().equals(trainSearchRequest.getSourceStationId())).findFirst().orElse(null);
            Map<CoachType,Integer> coachTypeMap = new HashMap<>();
            Map<CoachType,Double> coachTypePriceMap = new HashMap<>();

            for(TrainSeat trainSeat:trainSchedule.getTrainSeats()){
                coachTypeMap.merge(trainSeat.getCoachType(),1,Integer::sum);
                coachTypePriceMap.putIfAbsent(trainSeat.getCoachType(),trainSeat.getPrice());
            }

            assert trainRoute != null;
            AvailabeTrainResponse availabeTrainResponse1 = AvailabeTrainResponse.builder()
                    .traindId(train.getId())
                    .trainNumber(train.getTrainNo())
                    .trainName(train.getName())
                    .departureTime(trainRoute.getDepartureTime())
                    .arrivalTime(trainRoute.getArrivalTime())
                    .seatsAvailable(coachTypeMap)
                    .priceByCoach(coachTypePriceMap)
                    .scheduledDate(trainSchedule.getRunDate())
                    .trainScheduleId(trainSchedule.getId())
                    .build();
        }

     return  availabeTrainResponse;
    }
}
