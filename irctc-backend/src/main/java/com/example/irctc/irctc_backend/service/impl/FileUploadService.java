package com.example.irctc.irctc_backend.service.impl;

import com.example.irctc.irctc_backend.Entity.ImageMetaData;
import com.example.irctc.irctc_backend.helper.Helper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
@Service
public class FileUploadService  implements com.example.irctc.irctc_backend.service.FileUploadService {
    @Value("${file.upload.folder}")
    private String folder;
    @Override
    public ImageMetaData upload(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        InputStream inputStream = file.getInputStream();
        // create Folder if file not exists
        if(!Files.exists(Paths.get(folder))){
            Files.createDirectories(Paths.get(folder));
        }
        String fileNameWithPath = Helper.getFileName(folder,originalFileName);
        //lests upload the file
        Files.copy(file.getInputStream(),Paths.get(fileNameWithPath), StandardCopyOption.REPLACE_EXISTING);
        ImageMetaData metaData = new ImageMetaData();
        metaData.setField(UUID.randomUUID().toString());
        metaData.setFileName(fileNameWithPath);
        metaData.setFileSize(file.getSize());
        metaData.setContentType(file.getContentType());
        return metaData;
    }
}
