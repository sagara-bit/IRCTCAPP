package com.example.irctc.irctc_backend.service;

import com.example.irctc.irctc_backend.Entity.ImageMetaData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileUploadService {
 ImageMetaData upload(MultipartFile file) throws IOException;
}
