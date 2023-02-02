package com.showc.book.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Date;

@ControllerAdvice
public class CustomExceptionHandler {
    
    @ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<Object> handleBadRequest(RuntimeException runtimeException){
        ExceptionDetails exceptD = new ExceptionDetails(
                runtimeException.getMessage(),
                HttpStatus.BAD_REQUEST,
                new Date());
        return new ResponseEntity<>(exceptD, HttpStatus.BAD_REQUEST);
    }
}
