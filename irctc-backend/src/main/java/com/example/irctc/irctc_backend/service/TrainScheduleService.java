package com.example.irctc.irctc_backend.service;

import com.example.irctc.irctc_backend.Dto.TrainScheduleDto;

import java.util.List;

public interface TrainScheduleService {
    TrainScheduleDto createSchedule(TrainScheduleDto trainScheduleDto);
    List<TrainScheduleDto> getTrainSchedulesByTrainId(Long trainId);
    void  deleteTrainSchedule(long trainScheduleId);
    TrainScheduleDto updateTrainSchedule(Long trainScheduleId,TrainScheduleDto trainScheduleDto);

}
