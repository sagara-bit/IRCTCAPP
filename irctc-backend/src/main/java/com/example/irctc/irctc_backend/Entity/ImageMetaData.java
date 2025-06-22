package com.example.irctc.irctc_backend.Entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ImageMetaData {

    private String fileName;
    private  String field;
    private String contentType;
    private  long fileSize;
    private LocalDateTime updatedAt;

    public ImageMetaData(String fileName, String field, String contentType, long fileSize, LocalDateTime updatedAt) {
        this.fileName = fileName;
        this.field = field;
        this.contentType = contentType;
        this.fileSize = fileSize;
        this.updatedAt = updatedAt;
    }

    public ImageMetaData() {
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
