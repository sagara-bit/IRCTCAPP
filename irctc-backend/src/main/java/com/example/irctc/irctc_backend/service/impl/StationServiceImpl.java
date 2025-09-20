package com.example.irctc.irctc_backend.service.impl;

import com.example.irctc.irctc_backend.Dto.PagedResponse;
import com.example.irctc.irctc_backend.Dto.StationDto;
import com.example.irctc.irctc_backend.Dto.TrainDTO;
import com.example.irctc.irctc_backend.Entity.Station;
import com.example.irctc.irctc_backend.Entity.Train;
import com.example.irctc.irctc_backend.Exception.ResourceNotFoundException;
import com.example.irctc.irctc_backend.repositories.StationRepository;
import com.example.irctc.irctc_backend.service.StationService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class StationServiceImpl implements StationService {
    private StationRepository stationRepository;
    private ModelMapper modelMapper;
    public StationServiceImpl(StationRepository stationRepository,ModelMapper modelMapper){
        this.stationRepository = stationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PagedResponse<StationDto> listStations(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.trim().toLowerCase().equals("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).ascending();
        //Pagination
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<Station> stationPage = stationRepository.findAll(pageable);
        Page<StationDto> stationDto =stationPage.map(station -> modelMapper.map(station,StationDto.class));
        return PagedResponse.fromPage(stationDto);
    }

    @Override
    public StationDto createStatioon(StationDto stationDto) {
        Station station = modelMapper.map(stationDto,Station.class);
        Station savedStaion = stationRepository.save(station);
        return  modelMapper.map(savedStaion,StationDto.class);
    }

    @Override
    public StationDto getStationById(Long id) {
        Station station = stationRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("station not found with given id "));
        return  modelMapper.map(station,StationDto.class);
    }

    @Override
    public StationDto updateStation(Long id, StationDto stationDto) {
        Station station = stationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("station not found for update"));
        station.setName(stationDto.getName());
        station.setCode(stationDto.getCode());
        station.setCity(stationDto.getCity());
        station.setState(stationDto.getState());
        Station updateStation = stationRepository.save(station);
        return modelMapper.map(updateStation,StationDto.class);
    }

    @Override
    public void deleteStationById(Long id) {
        Station station = stationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("station not found for update"));
        stationRepository.delete(station);

    }
}
