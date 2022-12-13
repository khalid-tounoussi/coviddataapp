package com.coviddata.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomException extends RuntimeException {
    private final HttpStatus status;
    public CustomException( String message, HttpStatus status ){
        super(message);
        this.status = status;
    }
}
