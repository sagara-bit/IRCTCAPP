package com.example.irctc.irctc_backend.service.impl;

import com.example.irctc.irctc_backend.Entity.ImageMetaData;
import com.example.irctc.irctc_backend.service.FileUploadService;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Primary
public class AwsFileUploadService implements FileUploadService {
    @Override
    public ImageMetaData upload(MultipartFile file) throws IOException {

        //logix to upload  to AWS
        return null;
    }
}
