package com.test.bank.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@ControllerAdvice
public class ExceptionHandlingController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handling(BusinessException e){
        ErrorResponse error = new ErrorResponse(LocalDateTime.now().toString(), e.getMessage());
        return new ResponseEntity<>(error, BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> handling(NotFoundException exception){
        exception.printStackTrace();
        ErrorResponse errorDetails =
                new ErrorResponse(LocalDateTime.now().toString(), exception.getMessage());
        return new ResponseEntity<>(errorDetails, NOT_FOUND);
    }
}
