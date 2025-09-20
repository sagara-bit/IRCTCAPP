package com.example.irctc.irctc_backend.service;

import com.example.irctc.irctc_backend.Dto.PagedResponse;
import com.example.irctc.irctc_backend.Dto.StationDto;
import org.springframework.stereotype.Service;


public interface StationService {
     PagedResponse<StationDto> listStations(int page, int size, String sortBy, String sortDir);

    StationDto createStatioon(StationDto stationDto);

    StationDto getStationById(Long id);

    StationDto updateStation(Long id, StationDto stationDto);

    void deleteStationById(Long id);
}
