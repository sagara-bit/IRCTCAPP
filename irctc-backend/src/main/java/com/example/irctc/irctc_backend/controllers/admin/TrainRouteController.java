package com.example.irctc.irctc_backend.controllers.admin;

import com.example.irctc.irctc_backend.Dto.TrainRouteDto;
import com.example.irctc.irctc_backend.Entity.TrainRoute;
import com.example.irctc.irctc_backend.service.TrainRouteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/train-routes")
public class TrainRouteController {
    private final TrainRouteService trainRouteService;

    public TrainRouteController(TrainRouteService trainRouteService) {
        this.trainRouteService = trainRouteService;
    }

    @PostMapping
    public ResponseEntity<TrainRouteDto> createTrain( @RequestBody TrainRouteDto trainRouteDto){

        TrainRouteDto createdRoute = trainRouteService.addRoute(trainRouteDto);
        System.out.println(createdRoute.getId() +"getRoutesByTrain");

        return ResponseEntity.status(201).body(createdRoute);
    }

    @GetMapping("/train/{trainId}")
    public ResponseEntity<List<TrainRouteDto>> getRoutedByTrain(@PathVariable Long trainId){
        List<TrainRouteDto> route = trainRouteService.getRoutesByTrain(trainId);
        return ResponseEntity.ok(route);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<TrainRouteDto> updateTrainRoute(@PathVariable Long id, @RequestBody TrainRouteDto trainRouteDto){
        TrainRouteDto updateRoute = trainRouteService.updateRote(id,trainRouteDto);
        return ResponseEntity.ok(updateRoute);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainRoute(@PathVariable Long id){
        trainRouteService.deleteRoute(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
