package com.example.irctc.irctc_backend.Dto;

import com.example.irctc.irctc_backend.Entity.TrainImage;
import org.springframework.core.io.Resource;

public record TrainImageDataWithResource(TrainImage trainImage,
                                         Resource resource) {


}
