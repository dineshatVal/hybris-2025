package com.custom.occ.controllers;

import com.sample.module.core.dto.CustomException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(ex.getCustomMessage(), HttpStatus.BAD_REQUEST);
    }

    /*@ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        // Create a CustomException with the message from the RuntimeException
        CustomException customEx = new CustomException(ex.getMessage());
        // Delegate to the existing handleCustomException method
        return handleCustomException(customEx);
    }*/
}
