package com.product.exception;

import java.time.LocalDate;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.*;
import org.springframework.web.context.request.*;
//import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.serviet.mvc.method.annotation.ResponseEntityExceptionHandler;

public class RestExceptionHandler extends ResponseEntityExceptionHandler{

    @ExceptionHandler(ApiException.class)
    protected ResponseEntity<ExceptionResponse> handleApiException(ApiException exception, WebRequest request){
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(exception.getStatus().value());
        response.setError(exception.getStatus());
        response.setMessage(exception.getMessage());
        response.setPath(((ServletWebRequest)request),getStatus().getRequestURI().toString());
        return new ResponseEntity<>(response,response.getError());
    } 

    @ExceptionHandler(DBAccessException.class)
    protected ResponseEntity<ExceptionResponse> handleDBAccessException(DBAccessException exception, WebRequest request){
        ExceptionResponse response = new ExceptionResponse();
        response.setTimestamp(LocalDateTime.now());
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.setError(HttpsStatus.INTERNAL_SERVER_ERROR);
        response.setMessage("Error al acceder a la base de datos");
        response.setPath(((ServletWebRequest)request),getStatus().getRequestURI().toString());
        return new ResponseEntity<>(response,response.getError());
    } 
}