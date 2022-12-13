package com.showc.book.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Date;

public class ExceptionDetails {
    private final String message;
    private final HttpStatus httpStatus;
    private final Date timestamp;

    public ExceptionDetails(String message, HttpStatus httpStatus, Date timestamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public Date getTimestamp() {
        return timestamp;
    }
}
