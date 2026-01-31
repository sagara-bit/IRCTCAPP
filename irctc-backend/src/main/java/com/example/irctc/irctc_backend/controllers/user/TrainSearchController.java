package com.example.irctc.irctc_backend.controllers.user;

import com.example.irctc.irctc_backend.Dto.AvailabeTrainResponse;
import com.example.irctc.irctc_backend.Dto.UserTrainSearchRequest;
import com.example.irctc.irctc_backend.service.TrainService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user/trains")
public class TrainSearchController {

    private final TrainService trainService;
    public TrainSearchController(TrainService trainService) {
        this.trainService = trainService;
    }
    //search Train
    @PostMapping("/search")
    public ResponseEntity<List<AvailabeTrainResponse>> searchTrain(@RequestBody UserTrainSearchRequest trainSearchRequest) {

       List<AvailabeTrainResponse> availabeTrainResponses = trainService.searchTrains(trainSearchRequest);
       return new ResponseEntity<>(availabeTrainResponses, HttpStatus.OK);
    }
}
