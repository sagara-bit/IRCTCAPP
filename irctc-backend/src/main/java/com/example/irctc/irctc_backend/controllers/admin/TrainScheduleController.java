package com.example.irctc.irctc_backend.controllers.admin;

import com.example.irctc.irctc_backend.Dto.TrainScheduleDto;
import com.example.irctc.irctc_backend.Entity.TrainSchedule;
import com.example.irctc.irctc_backend.service.TrainScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train-schedules")
public class TrainScheduleController {
    @Autowired
 private TrainScheduleService trainScheduleService;

    @PostMapping
    public ResponseEntity<TrainScheduleDto> createTrainSchedule(@RequestBody TrainScheduleDto trainScheduleDto){
        TrainScheduleDto trainScheduleDto1 = trainScheduleService.createSchedule(trainScheduleDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(trainScheduleDto1);
    }

    @GetMapping("/train/{trainId}")
    public List<TrainScheduleDto> getTrainSchedulesByTrainId(@PathVariable Long trainId){
        return  trainScheduleService.getTrainSchedulesByTrainId(trainId);
    }

    @DeleteMapping("/{trainScheduledId}")
    public ResponseEntity<Void> deleteTrainSchedule(@PathVariable Long trainScheduleId){
        trainScheduleService.deleteTrainSchedule(trainScheduleId);
        return  ResponseEntity.noContent().build();
    }

    //update Train

    @PutMapping("/{trainScheduledId}")
    public ResponseEntity<TrainScheduleDto> updateTrainSchedule(@PathVariable Long trainScheduleId,@RequestBody TrainScheduleDto trainScheduleDto){
        TrainScheduleDto updatedSchedule = trainScheduleService.updateTrainSchedule(trainScheduleId,trainScheduleDto);
        return  ResponseEntity.ok(updatedSchedule);
    }

}
