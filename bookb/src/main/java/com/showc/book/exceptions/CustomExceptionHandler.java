package com.showc.book.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletResponse;
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
