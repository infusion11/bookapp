package com.showc.book.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.util.Date;

@ControllerAdvice
public class GlobalExceptionHandler{
    //Valid annotation msg response
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handleGlobalExceptions(MethodArgumentNotValidException methodArgumentNotValidException){
        ExceptionDetails exceptD = new ExceptionDetails(
                methodArgumentNotValidException.getFieldError().getDefaultMessage(),
                HttpStatus.BAD_REQUEST,
                new Date());
        return new ResponseEntity<>(exceptD, HttpStatus.BAD_REQUEST);
    }
}