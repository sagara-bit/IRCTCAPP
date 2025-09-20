package com.example.irctc.irctc_backend.controllers.admin;

import com.example.irctc.irctc_backend.Dto.TrainSeatDto;
import com.example.irctc.irctc_backend.service.TrainSeatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train-seats")
public class TrainSeatController {

    private TrainSeatService trainSeatService;

    public TrainSeatController(TrainSeatService trainSeatService) {
        this.trainSeatService = trainSeatService;
    }

    //create
    @PostMapping
    public ResponseEntity<TrainSeatDto> createSeat(@RequestBody TrainSeatDto trainSeatDto){
        TrainSeatDto createdSeat = trainSeatService.createSeatInformation(trainSeatDto);
        return ResponseEntity.status(201).body(createdSeat);
    }

    //get seats of train schedule
    @GetMapping("/schedule/{scheduleId}")
    public  ResponseEntity<List<TrainSeatDto>> getSeatsByScheduledId(@PathVariable Long scheduleId){
        List<TrainSeatDto> seatDto = trainSeatService.getSeatInfoByTrainScheduleId(scheduleId);
        return ResponseEntity.ok(seatDto);


    }

    //delete seat info

    @DeleteMapping("/{seatId}")
    public ResponseEntity<Void> deleteSeatInfo(@PathVariable Long seatId){
        trainSeatService.deleteSeatInfo(seatId);
        return ResponseEntity.noContent().build();
    }

    /// update Seat info
    @PutMapping("/{seatId}")
    public ResponseEntity<TrainSeatDto> updateSeatInfo(@PathVariable Long seatId, @RequestBody TrainSeatDto trainSeatDto){
        TrainSeatDto updatedSeat = trainSeatService.updateSeatInfo(seatId,trainSeatDto);
        return  ResponseEntity.ok(updatedSeat);
    }
}
