package com.example.irctc.irctc_backend.service;

import com.example.irctc.irctc_backend.Dto.TrainRouteDto;

import java.util.List;

public interface TrainRouteService {

    TrainRouteDto addRoute(TrainRouteDto trainRouteDto);

    List<TrainRouteDto> getRoutesByTrain(Long traindId);

    TrainRouteDto updateRote(Long id,TrainRouteDto trainRouteDto);

    void deleteRoute(Long id);
}
