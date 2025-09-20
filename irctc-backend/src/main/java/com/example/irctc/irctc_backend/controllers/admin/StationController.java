package com.example.irctc.irctc_backend.controllers.admin;

import com.example.irctc.irctc_backend.Dto.PagedResponse;
import com.example.irctc.irctc_backend.Dto.StationDto;
import com.example.irctc.irctc_backend.config.AppConstants;
import com.example.irctc.irctc_backend.service.StationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/stations")
public class StationController {

    private StationService stationService;
    public StationController(StationService stationService){
        this.stationService = stationService;
    }

    //create station
    @PostMapping
    public ResponseEntity<StationDto> createStation( @Valid  @RequestBody StationDto stationDto){
        StationDto dto = stationService.createStatioon(stationDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);

    }
    //to get the list of stations
    @GetMapping("/list")
    public PagedResponse<StationDto> listStation(
            @RequestParam(value = "page",defaultValue = AppConstants.PAGE_VALUE) int page,
            @RequestParam(value = "size",defaultValue = AppConstants.DEFAULT_PAGE_SIZE                                                                                                                                                      ) int size,
            @RequestParam(value = "sortby",defaultValue = "name") String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc") String sortDir
    ){
        PagedResponse<StationDto> stationsDto = stationService.listStations(page,size,sortBy,sortDir);
        return stationsDto;
    }

    // to get station by id
    @GetMapping("/{id}")
    public  StationDto getStationById(@PathVariable Long id){
        StationDto stationDto = stationService.getStationById(id);
         return  stationDto;
    }


    @PutMapping("/{id}")
    public  StationDto updateStation(@PathVariable Long id,@RequestBody StationDto stationDto){
        StationDto stationDtoFromService = stationService.updateStation(id,stationDto);
        return  stationDtoFromService;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStation(
        @PathVariable Long id
    ){
        stationService.deleteStationById(id);
        return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
