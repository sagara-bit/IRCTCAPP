package com.example.irctc.irctc_backend.service;

import com.example.irctc.irctc_backend.Dto.TrainImageDataWithResource;
import com.example.irctc.irctc_backend.Dto.TrainImageResponse;
import com.example.irctc.irctc_backend.Entity.Train;
import com.example.irctc.irctc_backend.Entity.TrainImage;
import com.example.irctc.irctc_backend.Exception.ResourceNotFoundException;
import com.example.irctc.irctc_backend.config.AppConstants;
import com.example.irctc.irctc_backend.repositories.TrainImageRepository;
import com.example.irctc.irctc_backend.repositories.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class TrainImageService {
    @Value("${train.image.folder.path}")
    private  String folderPath;

    @Autowired
    private TrainImageRepository trainImageRepository;

    @Autowired
    private TrainRepository trainRepository;

    public TrainImageResponse upload(MultipartFile file,Long trainNo) throws IOException {
        Train train = trainRepository.findById(trainNo).orElseThrow(() -> new ResourceNotFoundException("train Not Found"));
        if(!Files.exists(Paths.get(folderPath))){
            System.out.println("Creating Folder");
            Files.createDirectories(Paths.get(folderPath));
        }

        String fullFilePath= folderPath + UUID.randomUUID() +"_" + file.getOriginalFilename();
        Files.copy(file.getInputStream(),Paths.get(fullFilePath), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("File uppaded");
        TrainImage trainImage = new TrainImage();
        trainImage.setFileName(fullFilePath);
        trainImage.setFileType(file.getContentType());
        trainImage.setSize(file.getSize());

        trainImage.setTrain(train);
        train.setTrainImage(trainImage);
      Train savedTrain = trainRepository.save(train);

        return TrainImageResponse.from(savedTrain.getTrainImage(), AppConstants.BASE_URL,trainNo);


    }

    public TrainImageDataWithResource loadIImageByTraiNO(Long trainId) throws MalformedURLException {
        // get the train using train no
         Train train = trainRepository.findById(trainId).orElseThrow(()-> new ResourceNotFoundException("Train Not Found"));
         TrainImage trainImage = train.getTrainImage();
         if(trainImage==null){
             throw new ResourceNotFoundException("Image not Found");
         }
         Path path = Paths.get(trainImage.getFileName());
         if(!Files.exists(path)){
             throw  new ResourceNotFoundException("Image Not Found");
         }
         UrlResource urlResource = new UrlResource(path.toUri());
         TrainImageDataWithResource trainImageDataWithResource = new TrainImageDataWithResource(trainImage,urlResource);
         return trainImageDataWithResource;
    }
}
