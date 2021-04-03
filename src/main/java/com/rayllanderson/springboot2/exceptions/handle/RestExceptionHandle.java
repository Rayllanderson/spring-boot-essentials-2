package com.rayllanderson.springboot2.exceptions.handle;

import com.rayllanderson.springboot2.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandle {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> handleNotFoundException(NotFoundException e, HttpServletRequest request){
        HttpStatus statusCode = HttpStatus.NOT_FOUND;
        return ResponseEntity.status(statusCode).body(StandardError.builder()
        .timestamp(LocalDateTime.now())
        .error("Not Found")
        .message(e.getMessage())
        .path(request.getRequestURI())
        .status(statusCode.value()).build());
    }

}
