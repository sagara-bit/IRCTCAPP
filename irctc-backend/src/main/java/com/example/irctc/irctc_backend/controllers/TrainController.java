package com.example.irctc.irctc_backend.controllers;


import com.example.irctc.irctc_backend.Dto.ErrorResponse;
import com.example.irctc.irctc_backend.Dto.PagedResponse;
import com.example.irctc.irctc_backend.Dto.TrainDTO;
import com.example.irctc.irctc_backend.Entity.ImageMetaData;
import com.example.irctc.irctc_backend.Entity.Train;
import com.example.irctc.irctc_backend.service.FileUploadService;
import com.example.irctc.irctc_backend.service.TrainService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
//controller + responseBody
@RequestMapping("/trains")
public class TrainController {
    @Autowired
   private TrainService trainService;
    @Autowired
    private FileUploadService fileUploadService;

    @PostMapping("/photo")
    public ImageMetaData uploadTrainPhoto(@RequestParam("file") MultipartFile file ) throws IOException {

        ImageMetaData imageMetaData = fileUploadService.upload(file);
        //using repository we can save in database;

        return imageMetaData;
    }

    @GetMapping
    //@RequestMappin(value="/",method = RequestMethod.GET)
    public PagedResponse<TrainDTO> listOfTrain(
            @RequestParam(value="page",defaultValue = "0") int page,
            @RequestParam(value="size",defaultValue = "10") int size,
            @RequestParam(value="sortBy",defaultValue = "name") String sortBy,
            @RequestParam(value="sortDir",defaultValue = "asc") String sortDir

    ){
        return this.trainService.all(page,size,sortBy,sortDir);
    }

    //get single

    @GetMapping("/{trainNo}")
    public ResponseEntity<TrainDTO> get( @PathVariable String trainNo){
        return  new ResponseEntity<>(this.trainService.get(trainNo),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TrainDTO> add(@Valid  @RequestBody TrainDTO trainDTO){
        return new ResponseEntity<>(this.trainService.add(trainDTO), HttpStatus.CREATED);
    }

    @DeleteMapping("/{trainNo}")
    public void delete(@PathVariable String trainNo){
        this.trainService.delete(trainNo);
    }

//    @ExceptionHandler(NoSuchElementException.class)
//    public ErrorResponse handlNoSuchException(NoSuchElementException exception){
//     ErrorResponse response = new ErrorResponse("Train Not Found" + exception.getMessage(),"404",false);
//     return  response;
//    }

}
