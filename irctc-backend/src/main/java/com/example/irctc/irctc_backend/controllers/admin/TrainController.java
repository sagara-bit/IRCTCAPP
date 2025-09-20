package com.example.irctc.irctc_backend.controllers.admin;

import com.example.irctc.irctc_backend.Dto.TrainDTO;
import com.example.irctc.irctc_backend.service.TrainService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("adminTrainController")
@RequestMapping("/admin/trains")
@Tag(name="Train Management")
public class TrainController {
    @Autowired
   TrainService trainService;

    @Operation(
            summary = "create Train",
            description = "This Api create new Trains"
    )
    @PostMapping
    public ResponseEntity<TrainDTO> createTrain(
        @RequestBody TrainDTO trainDTO
    ){
        return new ResponseEntity<>(trainService.createTrain(trainDTO),HttpStatus.CREATED);
    }
    @GetMapping
    public List<TrainDTO> getAllTrain(){
        return trainService.getAllTrains();
    }
    @GetMapping("/{id}")
    public ResponseEntity<TrainDTO> getTrainById(@PathVariable("id") Long id){
        return new ResponseEntity<>(trainService.getTrainById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainDTO> updateTrain(@PathVariable("id") Long id, @RequestBody TrainDTO trainDTO){
        return new ResponseEntity<>(trainService.updateTrain(id,trainDTO),HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrain(@PathVariable("id") Long id){
        trainService.deleteTrain(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
