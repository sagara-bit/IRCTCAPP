package com.example.irctc.irctc_backend.Exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(){
        super("Resource Not Found !!");
    }
    public ResourceNotFoundException(String message){
        super(message);
    }
}
