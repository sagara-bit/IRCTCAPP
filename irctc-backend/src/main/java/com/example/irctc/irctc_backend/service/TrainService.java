package com.example.irctc.irctc_backend.service;

import com.example.irctc.irctc_backend.Dto.PagedResponse;
import com.example.irctc.irctc_backend.Dto.TrainDTO;
import com.example.irctc.irctc_backend.Entity.Train;
import com.example.irctc.irctc_backend.Exception.ResourceNotFoundException;
import com.example.irctc.irctc_backend.repositories.TrainRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TrainService {
    List<Train> trainList = new ArrayList<>();

    private TrainRepository trainRepository;
    private ModelMapper modelMapper;
    @Autowired
    public  TrainService (TrainRepository trainRepository,ModelMapper modelMapper){
        this.trainRepository = trainRepository;
        this.modelMapper = modelMapper;
    }

    public TrainService(){
//
    }

    public TrainDTO add(TrainDTO trainDTO){

        // convert DTO to entitiy

//     Train train = new Train();
//     train.setTrainNo(trainDTO.getTrainNo());
//     train.setName(trainDTO.getName());
//     train.setRouteName(trainDTO.getRouteName());

        Train train = modelMapper.map(trainDTO,Train.class);

      Train savedTrain = trainRepository.save(train);

      //convert entity into object

//        TrainDTO dto = new TrainDTO();
//        dto.setName(savedTrain.getName());
//        dto.setRouteName(savedTrain.getRouteName());
//        dto.setTrainNo(savedTrain.getTrainNo());

        TrainDTO dto = modelMapper.map(savedTrain,TrainDTO.class);
      return  dto;
    }

    public PagedResponse<TrainDTO> all(int page, int size, String sortBy, String sortDir){
    //Sorting
        Sort sort = sortDir.trim().toLowerCase().equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
    //Pagination
        Pageable pageable = PageRequest.of(page,size,sort);
      Page<Train> trainPage = trainRepository.findAll(pageable);
//      List<Train> trains = trainPage.getContent();
//
//      List<TrainDTO> trainDTOS = trains.stream().map(train -> modelMapper.map(train,TrainDTO.class)).toList();

        //list of train to list of  train Dtos


              Page<TrainDTO> trainDTOPage =trainPage.map(train -> modelMapper.map(train,TrainDTO.class));
              return PagedResponse.fromPage(trainDTOPage);
    }

    public TrainDTO get(String trainNo){

        Train train = trainRepository.findById(trainNo).orElseThrow(()-> new ResourceNotFoundException("Train not found"));
        return  modelMapper.map(train,TrainDTO.class);
    }

    public void delete(String trainNo){
         Train train = trainRepository.findById(trainNo).orElseThrow(()-> new ResourceNotFoundException("Train Not Found"));

         trainRepository.delete(train);
    }

}
