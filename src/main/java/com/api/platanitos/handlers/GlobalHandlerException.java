package com.api.platanitos.handlers;

import java.time.LocalDateTime;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.platanitos.dtos.api.ResponseError;

@RestControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseError> handlerRuntimeException(RuntimeException e){
        return ResponseEntity.badRequest().body(
            ResponseError.builder()
                .success(false)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build()   
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handlerException(Exception e){
        return ResponseEntity.badRequest().body(
            ResponseError.builder()
                .success(false)
                .message(e.getMessage())
                .timestamp(LocalDateTime.now())
                .build()   
        );
    }
}
