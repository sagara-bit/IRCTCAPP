package com.example.irctc.irctc_backend.Exception;


import com.example.irctc.irctc_backend.Dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

//@ControllerAdvice
////for resting view also
///
@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ErrorResponse> handlNoSuchException(NoSuchElementException exception){
     ErrorResponse response = new ErrorResponse("Train Not Found" + exception.getMessage(),"404",false);
     ResponseEntity<ErrorResponse> responseResponseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
     return  responseResponseEntity;
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResouceNotFoundException(ResourceNotFoundException exception){
        ErrorResponse response = new ErrorResponse("Train Not Found" + exception.getMessage(),"404",false);
        ResponseEntity<ErrorResponse> responseResponseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        return  responseResponseEntity;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String,String>> handleMethodArugmentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException){

            Map<String,String>  errorResponse = new HashMap<>();
            methodArgumentNotValidException.getBindingResult().getAllErrors().forEach(error ->{
                String errorMessage = error.getDefaultMessage();
                String field =((FieldError)error).getField();
                errorResponse.put(field,errorMessage);
            });
            ResponseEntity<Map<String,String>> error = new ResponseEntity<>(errorResponse,HttpStatus.BAD_REQUEST);
            return error;
    }


}
