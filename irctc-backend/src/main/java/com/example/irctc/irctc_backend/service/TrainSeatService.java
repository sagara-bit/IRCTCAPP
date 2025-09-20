package com.example.irctc.irctc_backend.service;

import com.example.irctc.irctc_backend.Dto.TrainSeatDto;

import java.util.List;

public interface TrainSeatService {
    TrainSeatDto createSeatInformation(TrainSeatDto trainSeatDto);
    List<TrainSeatDto> getSeatInfoByTrainScheduleId(Long scheduleId);
    void  deleteSeatInfo(Long seatId);
    TrainSeatDto updateSeatInfo(Long seatId,TrainSeatDto trainSeatDto);
    List<Integer> bookSeat(int seatToBook,Long seatId);
}
